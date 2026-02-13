package com.kk.mumuchat.mqtt.client.handle;

import com.kk.mumuchat.mqtt.basicClient.handle.MqttBaseSendHandle;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Component;

/**
 * MQTT发送客户端消息处理 | 默认
 *
 * @author mumuchat
 */
@Slf4j
@Component
public class MqttSendHandle implements MqttBaseSendHandle {

    /**
     * 判断处理类型
     *
     * @param topic   订阅主题
     * @param message 消息内容
     * @return 结果
     */
    @Override
    public boolean support(String topic, MqttMessage message) {
        return Boolean.TRUE;
    }

    /**
     * 消息处理
     *
     * @param topic   订阅主题
     * @param message 消息内容
     */
    @Override
    public void handle(String topic, MqttMessage message) {
        log.info("【接收消息主题】: {}", topic);
        log.info("【接收消息Qos】: {}", message.getQos());
        log.info("【接收消息内容】: {}", new String(message.getPayload()));
    }

    /**
     * 处理优先级 | 从大到小
     *
     * @return 优先级
     */
    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE;
    }
}
