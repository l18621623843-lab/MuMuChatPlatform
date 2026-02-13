package com.kk.mumuchat.common.mq.rabbit.service.impl;

import com.kk.mumuchat.common.core.constant.basic.EvnConstants;
import com.kk.mumuchat.common.core.utils.core.CollUtil;
import com.kk.mumuchat.common.core.utils.core.ObjectUtil;
import com.kk.mumuchat.common.core.utils.core.StrUtil;
import com.kk.mumuchat.common.mq.rabbit.config.properties.RabbitExchangeProperties;
import com.kk.mumuchat.common.mq.rabbit.domain.MqMsgInfo;
import com.kk.mumuchat.common.mq.rabbit.service.RabbitService;
import jakarta.annotation.Resource;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * RabbitMQ消息 工具类
 *
 * @author mumuchat
 **/
@Slf4j
@Component
public class RabbitServiceImpl implements RabbitService {

    @Getter
    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private RabbitExchangeProperties rabbitExchangeProperties;

    @Bean("rabbitMQEvn")
    public String rabbitMQEvn() {
        return getEvnPrefix();
    }

    private String getEvnPrefix() {
        return rabbitExchangeProperties.getEvnPrefix();
    }

    public String getEvnPrefix(String key) {
        return getEvnPrefix() + key;
    }

    @Override
    @SneakyThrows
    public void convertAndSend(String exchangeName, String routingKey, Object object) {
        rabbitTemplate.convertAndSend(getEvnPrefix(exchangeName), getEvnPrefix(routingKey), object);
    }

    @Override
    @SneakyThrows
    public void convertAndSendWithEvn(EvnConstants.EvnType evn, String exchangeName, String routingKey, Object object) {
        String prefix = StrUtil.isNotBlank(rabbitExchangeProperties.getPrefix()) ? StrUtil.format("{}.", rabbitExchangeProperties.getPrefix()) : StrUtil.EMPTY;
        rabbitTemplate.convertAndSend(StrUtil.format("{}{}.{}", prefix, evn.getCode(), exchangeName), StrUtil.format("{}{}.{}", prefix, evn.getCode(), routingKey), object);
    }

    @Override
    @SneakyThrows
    public void convertAndSendDelayed(String exchangeName, String routingKey, Object object, long delayInTime, long expirationSeconds, TimeUnit timeUnit) {
        // 将delayInTime转换为毫秒
        long delayInMillis = Optional.ofNullable(timeUnit)
                .map(unit -> unit.toMillis(delayInTime))
                .orElse(delayInTime);
        log.info("发送延时消息：交换机名称：{}，路由键：{}，消息内容：{}，延时时间：{}秒", getEvnPrefix(exchangeName), getEvnPrefix(routingKey), object, delayInTime/1000);
        rabbitTemplate.convertAndSend(getEvnPrefix(exchangeName), getEvnPrefix(routingKey), object,
                message -> {
                    // 设置延时时间（毫秒）
                    message.getMessageProperties().setDelayLong(delayInMillis);
                    // 设置消息持续时间（毫秒）
                    message.getMessageProperties().setExpiration(String.valueOf(expirationSeconds * 1000));
                    return message;
                });
    }

    @Override
    @SneakyThrows
    public void sendMsg(MqMsgInfo mqMsgInfo) {
        Optional.ofNullable(mqMsgInfo).ifPresent(info -> {
            if (ObjectUtil.isNotNull(info.getDelayTime()) && info.getDelayTime() > 0) {
                convertAndSendDelayed(info.getExchangeName(), info.getRoutingKey(), info.getMsgInfo(), info.getDelayTime(), 300, ObjectUtil.getObjOrNullElse(info.getTimeUnit(),TimeUnit.SECONDS));
            } else {
                convertAndSend(info.getExchangeName(), info.getRoutingKey(), info.getMsgInfo());
            }
        });
    }

    @Override
    @SneakyThrows
    public void batchSendMsg(List<MqMsgInfo> mqMsgInfoList) {
        Optional.ofNullable(mqMsgInfoList).filter(CollUtil::isNotEmpty).ifPresent(list ->
                list.forEach(this::sendMsg));
    }
}
