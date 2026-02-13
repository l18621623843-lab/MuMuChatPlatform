package com.kk.mumuchat.mqtt.basicClient.handle;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.core.Ordered;

/**
 * MQTT发送客户端消息处理 | 基类
 *
 * @author mumuchat
 */
public interface MqttBaseSendHandle extends Ordered {

    /**
     * 判断处理类型
     *
     * @param topic   订阅主题
     * @param message 消息内容
     * @return 结果
     */
    boolean support(String topic, MqttMessage message);

    /**
     * 消息处理
     *
     * @param topic   订阅主题
     * @param message 消息内容
     */
    void handle(String topic, MqttMessage message);

    /**
     * 处理优先级 | 从大到小
     *
     * @return 优先级
     */
    @Override
    default int getOrder() {
        return LOWEST_PRECEDENCE;
    }
}
