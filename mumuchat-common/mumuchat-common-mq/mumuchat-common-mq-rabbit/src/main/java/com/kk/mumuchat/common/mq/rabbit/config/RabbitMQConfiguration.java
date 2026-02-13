package com.kk.mumuchat.common.mq.rabbit.config;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.kk.mumuchat.common.core.exception.UtilException;
import com.kk.mumuchat.common.core.utils.core.CollUtil;
import com.kk.mumuchat.common.core.utils.core.MapUtil;
import com.kk.mumuchat.common.core.utils.core.ObjectUtil;
import com.kk.mumuchat.common.core.utils.core.StrUtil;
import com.kk.mumuchat.common.mq.rabbit.config.properties.RabbitExchangeProperties;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * RabbitMQ 配置类
 *
 * @author mumuchat
 */
@Slf4j
@AutoConfiguration
@ConditionalOnClass(name = "org.springframework.amqp.rabbit.core.RabbitTemplate")
public class RabbitMQConfiguration {

    @Resource
    private RabbitExchangeProperties rabbitExchangeProperties;

    /** MQ初始化bean名 */
    public static final String MQ_RABBIT_INIT_METHOD = "rabbitMQConnectionFactory";

    @Resource
    private RabbitProperties rabbitBaseProperties;

    /**
     * RabbitMQ连接初始化
     * 注：修改rabbitMQConnectionFactory需要同步修改MQ_RABBIT_INIT_METHOD的方法名
     */
    @Bean
    @SneakyThrows
    public String rabbitMQConnectionFactory() {
        Optional.ofNullable(rabbitExchangeProperties).map(RabbitExchangeProperties::getExchangeInfos).filter(CollUtil::isNotEmpty).ifPresent(list -> {
            log.info("RabbitMQ连接初始化开始...");
            // 创建连接工厂
            ConnectionFactory factory = new ConnectionFactory();
            list.forEach(item -> {
                try {
                    String host = StrUtil.isNotBlank(item.getHost()) ? item.getHost() : rabbitBaseProperties.getHost();
                    Integer port = ObjectUtil.isNotNull(item.getPort()) ? item.getPort() : rabbitBaseProperties.getPort();
                    String username = StrUtil.isNotBlank(item.getUsername()) ? item.getUsername() : rabbitBaseProperties.getUsername();
                    String password = StrUtil.isNotBlank(item.getPassword()) ? item.getPassword() : rabbitBaseProperties.getPassword();
                    if (StrUtil.isBlank(host)) {
                        throw new UtilException("地址（host）为空");
                    } else if (StrUtil.isBlank(item.getType())) {
                        throw new UtilException("交换机类型（type）为空");
                    } else if (StrUtil.isBlank(item.getName())) {
                        throw new UtilException("交换机名称（name）为空");
                    }
                    String evnName = rabbitExchangeProperties.getEvnPrefix();
                    // 设置RabbitMQ服务器信息
                    factory.setHost(host);
                    factory.setPort(port);
                    factory.setUsername(username);
                    factory.setPassword(password);
                    // 创建一个新的连接
                    Connection connection = factory.newConnection();
                    // 创建一个通道
                    Channel channel = connection.createChannel();

                    try (connection; channel) {
                        // 声明一个Exchange
                        String exchangeName = StrUtil.format("{}{}", evnName, item.getName());
                        Map<String, Object> exchangeArguments = MapUtil.isNotEmpty(item.getParams()) ? item.getParams() : null;
                        channel.exchangeDeclare(exchangeName, item.getType(), item.getDurable(), item.getAutoDelete(), item.getExclusive(), exchangeArguments);
                        Optional.ofNullable(item.getQueueInfos()).filter(CollUtil::isNotEmpty).ifPresent(queueInfos -> queueInfos.forEach(queueInfo -> {
                            try {
                                // 声明一个Queue
                                String queueName = StrUtil.format("{}{}", evnName, queueInfo.getName());
                                Map<String, Object> arguments = MapUtil.isNotEmpty(queueInfo.getParams()) ? queueInfo.getParams() : null;
                                channel.queueDeclare(queueName, queueInfo.getDurable(), queueInfo.getExclusive(), queueInfo.getAutoDelete(), arguments);
                                List<String> routingKeyList = Optional.ofNullable(queueInfo.getRoutingKeyList()).orElseGet(ArrayList::new);
                                if(ObjectUtil.isNotNull(queueInfo.getRoutingKey())) {
                                    routingKeyList.add(queueInfo.getRoutingKey());
                                }
                                // 将Queue绑定到Exchange
                                if (CollUtil.isNotEmpty(routingKeyList)) {
                                    routingKeyList.forEach(routingKeyItem -> {
                                        String routingKeyNameItem = StrUtil.format("{}{}", evnName, routingKeyItem);
                                        try {
                                            channel.queueBind(queueName, exchangeName, routingKeyNameItem);
                                        } catch (IOException e) {
                                            log.error("RabbitMQ子连接创建失败, 队列名称: {}, 路由规则: {}, 原因：{}\n", queueName, routingKeyNameItem, e.getMessage(), e);
                                        }
                                    });
                                }
                            } catch (Exception e) {
                                log.error("RabbitMQ连接创建失败，原因：{}\n", e.getMessage(), e);
                            }
                        }));
                    }
                } catch (Exception e) {
                    log.error("RabbitMQ连接创建失败，原因：{}\n", e.getMessage(), e);
                }
            });
            log.info("RabbitMQ连接初始化完成");
        });
        return StrUtil.EMPTY;
    }

    /**
     * 消息序列化消息
     */
    @Bean
    public MessageConverter createMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
