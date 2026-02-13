package com.kk.mumuchat.common.mq.rabbit.service;

import com.kk.mumuchat.common.core.constant.basic.EvnConstants;
import com.kk.mumuchat.common.mq.rabbit.domain.MqMsgInfo;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * RabbitMQ消息 工具类
 *
 * @author mumuchat
 **/
public interface RabbitService {

    /**
     * 获取RabbitTemplate
     *
     * @return RabbitTemplate
     */
    RabbitTemplate getRabbitTemplate();

    /**
     * 获取系统环境前缀
     *
     * @return 结果
     */
    String getEvnPrefix(String key);

    /**
     * 发送消息
     *
     * @param exchangeName 交换机名称
     * @param routingKey   路由键
     * @param object       消息内容
     */
    void convertAndSend(String exchangeName, String routingKey, Object object);

    /**
     * 跨环境发送消息
     *
     * @param exchangeName 交换机名称
     * @param routingKey   路由键
     * @param object       消息内容
     */
    void convertAndSendWithEvn(EvnConstants.EvnType evn, String exchangeName, String routingKey, Object object);

    /**
     * 发送延时消息
     * <p>
     * 注意：
     * 1.队列已创建
     * 2.路由队列是否为延时队列
     *
     * @param exchangeName   交换机名称
     * @param routingKey     路由键
     * @param object         消息内容
     * @param delayInSeconds 延时时间（单位/秒）
     */
    default void convertAndSendDelayed(String exchangeName, String routingKey, Object object, long delayInSeconds) {
        convertAndSendDelayed(exchangeName, routingKey, object, delayInSeconds, 300, TimeUnit.SECONDS);
    }

    /**
     * 发送延时消息
     * <p>
     * 注意：
     * 1.队列已创建
     * 2.路由队列是否为延时队列
     *
     * @param exchangeName      交换机名称
     * @param routingKey        路由键
     * @param object            消息内容
     * @param delayInSeconds    延时时间（单位/秒）
     * @param expirationSeconds 消息有效时间（单位/秒）
     */
    default void convertAndSendDelayed(String exchangeName, String routingKey, Object object, long delayInSeconds, long expirationSeconds) {
        convertAndSendDelayed(exchangeName, routingKey, object, delayInSeconds, expirationSeconds, TimeUnit.SECONDS);
    }

    /**
     * 发送延时消息
     * <p>
     * 注意：
     * 1.队列已创建
     * 2.路由队列是否为延时队列
     *
     * @param exchangeName      交换机名称
     * @param routingKey        路由键
     * @param object            消息内容
     * @param delayInTime       延时时间（单位/时间单位）
     * @param expirationSeconds 消息有效时间（单位/秒）
     * @param timeUnit          时间单位
     */
    void convertAndSendDelayed(String exchangeName, String routingKey, Object object, long delayInTime, long expirationSeconds, TimeUnit timeUnit);

    /**
     * 发送消息
     *
     * @param mqMsgInfo 消息
     */
    void sendMsg(MqMsgInfo mqMsgInfo);

    /**
     * 批量发送消息
     *
     * @param mqMsgInfoList 消息列表
     */
    void batchSendMsg(List<MqMsgInfo> mqMsgInfoList);
}
