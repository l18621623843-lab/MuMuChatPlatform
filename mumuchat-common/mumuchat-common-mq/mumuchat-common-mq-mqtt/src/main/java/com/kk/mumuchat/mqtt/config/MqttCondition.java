package com.kk.mumuchat.mqtt.config;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * MQTT动态启动
 *
 * @author mumuchat
 */
public class MqttCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        //3、获取当前环境信息
        Environment environment = context.getEnvironment();
        String isOpen = environment.getProperty("xueyi.mq.mqtt.isOpen");
        return Boolean.parseBoolean(isOpen);
    }
}

