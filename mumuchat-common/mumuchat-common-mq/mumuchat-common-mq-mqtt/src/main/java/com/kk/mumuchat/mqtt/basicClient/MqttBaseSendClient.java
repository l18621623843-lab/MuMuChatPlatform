package com.kk.mumuchat.mqtt.basicClient;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.kk.mumuchat.mqtt.basicClient.callback.MqttBaseSendCallBack;
import com.kk.mumuchat.mqtt.config.properties.MqttProperties;
import com.kk.mumuchat.mqtt.service.MqttService;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.core.Ordered;

import java.util.Optional;
import java.util.UUID;

/**
 * MQTT发送客户端 | 基类
 *
 * @author xueyi
 */
@Slf4j
public abstract class MqttBaseSendClient<Callback extends MqttBaseSendCallBack, Properties extends MqttProperties> implements Ordered, MqttService {

    protected Callback callback;

    protected Properties properties;

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
     * 关闭连接
     *
     * @param mqttClient 连接信息
     */
    public static void disconnect(MqttClient mqttClient) {
        try {
            if (mqttClient != null)
                mqttClient.disconnect();
        } catch (MqttException e) {
            log.error("MqttSendClient disconnect error,message:", e);
        }
    }

    /**
     * 释放资源
     *
     * @param mqttClient 连接信息
     */
    public static void close(MqttClient mqttClient) {
        try {
            if (mqttClient != null)
                mqttClient.close();
        } catch (MqttException e) {
            log.error("MqttSendClient close error,message:", e);
        }
    }

    public MqttClient connect() {
        MqttClient client = null;
        try {
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            client = new MqttClient(getProperties().getHostUrl(), uuid, new MemoryPersistence());
            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName(getProperties().getUsername());
            options.setPassword(getProperties().getPassword().toCharArray());
            options.setConnectionTimeout(getProperties().getTimeout());
            options.setKeepAliveInterval(getProperties().getKeepAlive());
            options.setCleanSession(true);
            options.setAutomaticReconnect(false);
            // 设置回调
            client.setCallback(getCallback());
            client.connect(options);
        } catch (Exception e) {
            log.error("MqttSendClient connect error,message:", e);
        }
        return client;
    }

    public void cusTopicPublish(String topic, Object content) {
        // 将content转换为JSON对象
        JSONObject jsonObject = convertToJSONObject(content);
        // 将JSON对象中的Long类型字段转换为String
        JSONObject processedJson = convertLongToString(jsonObject);
        cusTopicPublish(Boolean.FALSE, topic, JSON.toJSONString(processedJson));
    }

    public void cusTopicPublish(String topic, String content) {
        cusTopicPublish(Boolean.FALSE, topic, content);
    }

    public void cusTopicPublish(boolean retained, String topic, String content) {
        MqttMessage message = new MqttMessage();
        message.setQos(getProperties().getQos());
        message.setRetained(retained);
        message.setPayload(content.getBytes());
        MqttClient mqttClient = connect();
        try {
            String actualTopic = getProperties().getServerTopic(topic);
            mqttClient.publish(actualTopic, message);
        } catch (MqttException e) {
            log.error("MqttSendClient publish error,message:", e);
        } finally {
            disconnect(mqttClient);
            close(mqttClient);
        }
    }

    protected JSONObject convertToJSONObject(Object obj) {
        // 如果obj已经是JSONObject，直接返回
        if (obj instanceof JSONObject) {
            return (JSONObject) obj;
        }
        // 否则将普通对象转换为JSONObject
        return JSON.parseObject(JSON.toJSONString(obj));
    }

    protected JSONObject convertLongToString(JSONObject jsonObject) {
        for (String key : jsonObject.keySet()) {
            Object value = jsonObject.get(key);
            if (value instanceof JSONObject) {
                // 递归处理嵌套的JSONObject
                jsonObject.put(key, convertLongToString((JSONObject) value));
            } else if (value instanceof JSONArray) {
                // 递归处理JSONArray
                jsonObject.put(key, convertLongToString((JSONArray) value));
            } else if (value instanceof Long) {
                // 将Long转换为String
                jsonObject.put(key, value.toString());
            }
        }
        return jsonObject;
    }

    protected JSONArray convertLongToString(JSONArray jsonArray) {
        for (int i = 0; i < jsonArray.size(); i++) {
            Object value = jsonArray.get(i);
            if (value instanceof JSONObject) {
                // 递归处理嵌套的JSONObject
                jsonArray.set(i, convertLongToString((JSONObject) value));
            } else if (value instanceof JSONArray) {
                // 递归处理嵌套的JSONArray
                jsonArray.set(i, convertLongToString((JSONArray) value));
            } else if (value instanceof Long) {
                // 将Long转换为String
                jsonArray.set(i, value.toString());
            }
        }
        return jsonArray;
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
