package com.kk.mumuchat.mqtt.service;

/**
 * MQTT消息 工具类
 *
 * @author mumuchat
 **/
public interface MqttService {

    /**
     * 自定义主题发布消息
     *
     * @param topic   主题
     * @param content 消息内容
     */
    void cusTopicPublish(String topic, Object content);

    /**
     * 自定义主题发布消息
     *
     * @param topic   主题
     * @param content 消息内容
     */
    void cusTopicPublish(String topic, String content);

    /**
     * 自定义主题发布消息
     *
     * @param retained 是否保留
     * @param topic    主题
     * @param content  消息内容
     */
    void cusTopicPublish(boolean retained, String topic, String content);
}
