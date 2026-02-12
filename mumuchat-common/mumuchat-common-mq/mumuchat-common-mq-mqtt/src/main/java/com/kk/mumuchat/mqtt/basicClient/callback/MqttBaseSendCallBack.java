package com.kk.mumuchat.mqtt.basicClient.callback;

import cn.hutool.extra.spring.SpringUtil;
import com.kk.mumuchat.mqtt.basicClient.handle.MqttBaseSendHandle;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.core.Ordered;

import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;

/**
 * MQTT发送客户端的回调 | 基类
 *
 * @author xueyi
 */
@Slf4j
public abstract class MqttBaseSendCallBack implements MqttCallbackExtended {

    /**
     * 客户端断开后触发
     */
    @Override
    public void connectionLost(Throwable throwable) {
        log.info("连接断开，可以重连");
    }

    /**
     * 客户端收到消息触发
     *
     * @param topic       主题
     * @param mqttMessage 消息
     */
    @Override
    @SneakyThrows
    public void messageArrived(String topic, MqttMessage mqttMessage) {
        Map<String, MqttBaseSendHandle> tokenServiceMap = SpringUtil.getBeansOfType(MqttBaseSendHandle.class);
        tokenServiceMap.values().stream()
                .filter(item -> item.support(topic, mqttMessage))
                .max(Comparator.comparingInt(Ordered::getOrder))
                .ifPresent(item -> item.handle(topic, mqttMessage));
    }

    /**
     * 发布消息成功
     *
     * @param token token
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        String[] topics = token.getTopics();
        for (String topic : topics) {
            log.info("向主题【{}】发送消息成功！", topic);
        }
        try {
            Optional.ofNullable(token.getMessage()).map(MqttMessage::getPayload)
                    .map(payload -> new String(payload, StandardCharsets.UTF_8))
                    .ifPresent(s -> log.info("【消息内容】: {}", s));
        } catch (Exception e) {
            log.error("MqttSendCallBack deliveryComplete error,message:", e);
        }
    }

    /**
     * 连接emq服务器后触发
     *
     * @param b
     * @param s
     */
    @Override
    public void connectComplete(boolean b, String s) {
        log.info("============================= 客户端【{}】连接成功！=============================", s);
    }
}