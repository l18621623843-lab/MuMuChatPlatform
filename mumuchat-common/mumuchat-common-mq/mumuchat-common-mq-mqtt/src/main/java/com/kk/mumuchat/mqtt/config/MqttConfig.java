package com.kk.mumuchat.mqtt.config;

import cn.hutool.extra.spring.SpringUtil;
import com.kk.mumuchat.mqtt.basicClient.MqttBaseAcceptClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import java.util.Comparator;
import java.util.Map;

/**
 * 服务监听客户端配置
 *
 * @author mumuchat
 */
@Configuration
@SuppressWarnings(value = {"rawtypes"})
public class MqttConfig {

    /**
     * 订阅MQTT
     */
    @Bean
    @Conditional(MqttCondition.class)
    public Boolean getMqttPushClient() {
        Map<String, MqttBaseAcceptClient> tokenServiceMap = SpringUtil.getBeansOfType(MqttBaseAcceptClient.class);
        tokenServiceMap.values().stream()
                .max(Comparator.comparingInt(Ordered::getOrder))
                .ifPresent(MqttBaseAcceptClient::connect);
        return Boolean.TRUE;
    }
}
