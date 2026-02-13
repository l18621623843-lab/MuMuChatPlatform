package com.kk.mumuchat.mqtt.basicClient;

import cn.hutool.core.util.StrUtil;
import com.kk.mumuchat.mqtt.basicClient.callback.MqttBaseAcceptCallback;
import com.kk.mumuchat.mqtt.config.properties.MqttProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.core.Ordered;

import java.util.Arrays;
import java.util.Optional;

/**
 * MQTT接受服务的客户端 | 基类
 *
 * @author mumuchat
 */
@Slf4j
@SuppressWarnings(value = {"rawtypes"})
public abstract class MqttBaseAcceptClient<Callback extends MqttBaseAcceptCallback, Properties extends MqttProperties> implements Ordered {

    @Getter
    @Setter
    protected MqttClient client;

    protected Callback callback;

    protected Properties properties;

//    protected MqttClient getClient() {
//        return Optional.ofNullable(client).orElse(setClient());
//    }
//
//    protected abstract void setClient();

    protected Callback getCallback() {
        return Optional.ofNullable(callback).orElseGet(this::setCallback);
    }

    protected Properties getProperties() {
        return Optional.ofNullable(properties).orElseGet(this::setProperties);
    }

    protected abstract Callback setCallback();

    protected abstract Properties setProperties();

    protected Callback setCallback(Callback callback) {
        this.callback = callback;
        return this.callback;
    }

    protected Properties setProperties(Properties properties) {
        this.properties = properties;
        return this.properties;
    }

    /**
     * 客户端连接
     */
    public void connect() {
        MqttClient client;
        try {
            client = new MqttClient(getProperties().getHostUrl(), getProperties().getUniqueClientId(),
                    new MemoryPersistence());
            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName(getProperties().getUsername());
            options.setPassword(getProperties().getPassword().toCharArray());
            options.setConnectionTimeout(getProperties().getTimeout());
            options.setKeepAliveInterval(getProperties().getKeepAlive());
            options.setAutomaticReconnect(getProperties().getReconnect());
            options.setCleanSession(getProperties().getCleanSession());
            this.setClient(client);
            // 设置回调
            client.setCallback(getCallback());
            client.connect(options);
        } catch (Exception e) {
            log.error("MqttAcceptClient connect error,message:", e);
        }
    }

    /**
     * 重新连接
     */
    public void reconnection() {
        try {
            getClient().connect();
        } catch (MqttException e) {
            log.error("MqttAcceptClient reconnection error,message:", e);
        }
    }

    /**
     * 订阅某个主题
     *
     * @param topic 主题
     * @param qos   连接方式
     */
    public void subscribe(String topic, int qos) {
        try {
            String actualTopic = getProperties().getServerTopic(topic);
            log.info("========================【开始订阅主题:{}】========================", actualTopic);
            getClient().subscribe(actualTopic, qos);
        } catch (MqttException e) {
            log.error("MqttAcceptClient subscribe error,message:", e);
        }
    }

    /**
     * 批量订阅某个主题
     *
     * @param topics 主题集合
     * @param qos    连接方式
     */
    public void subscribe(String[] topics, int[] qos) {
        try {
            String[] actualTopics = Arrays.stream(topics).filter(StrUtil::isNotBlank).map(item -> getProperties().getServerTopic(item)).toArray(String[]::new);
            log.info("========================【开始批量订阅主题:{}】========================", Arrays.toString(actualTopics));
            getClient().subscribe(actualTopics, qos);
        } catch (Exception e) {
            log.error("MqttAcceptClient batch subscribe error,message:", e);
        }
    }

    /**
     * 取消订阅某个主题
     *
     * @param topic 主题
     */
    public void unsubscribe(String topic) {
        try {
            String actualTopic = getProperties().getServerTopic(topic);
            log.info("========================【取消订阅主题:{}】========================", actualTopic);
            getClient().unsubscribe(actualTopic);
        } catch (MqttException e) {
            log.error("MqttAcceptClient unsubscribe error,message:", e);
        }
    }

    /**
     * 批量取消订阅某个主题
     *
     * @param topics 主题集合
     */
    public void unsubscribe(String[] topics) {
        try {
            String[] actualTopics = Arrays.stream(topics).filter(StrUtil::isNotBlank).map(item -> getProperties().getServerTopic(item)).toArray(String[]::new);
            log.info("========================【批量取消订阅主题:{}】========================", Arrays.toString(actualTopics));
            getClient().unsubscribe(actualTopics);
        } catch (Exception e) {
            log.error("MqttAcceptClient batch unsubscribe error,message:", e);
        }
    }

    /**
     * 处理优先级 | 从大到小
     *
     * @return 优先级
     */
    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE;
    }
}
