package com.kk.mumuchat.mqtt.basicClient.callback;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.kk.mumuchat.mqtt.basicClient.MqttBaseAcceptClient;
import com.kk.mumuchat.mqtt.basicClient.handle.MqttBaseAcceptHandle;
import com.kk.mumuchat.mqtt.config.properties.MqttProperties;
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
 * MQTT接受服务的回调 | 基类
 *
 * @author mumuchat
 */
@Slf4j
@SuppressWarnings(value = {"rawtypes"})
public abstract class MqttBaseAcceptCallback<Client extends MqttBaseAcceptClient<? extends MqttBaseAcceptCallback, Properties>, Properties extends MqttProperties> implements MqttCallbackExtended {

    protected Client client;

    protected Properties properties;

    protected Client getClient() {
        return Optional.ofNullable(client).orElseGet(this::setClient);
    }

    protected Properties getProperties() {
        return Optional.ofNullable(properties).orElseGet(this::setProperties);
    }

    protected abstract Client setClient();

    protected abstract Properties setProperties();

    protected Client setClient(Client client) {
        this.client = client;
        return this.client;
    }

    protected Properties setProperties(Properties properties) {
        this.properties = properties;
        return this.properties;
    }

    /**
     * 客户端断开后触发
     */
    @Override
    public void connectionLost(Throwable throwable) {
        synchronized (getClient()) {
            try {
                log.info("【连接断开，可以重连】");
                if (getClient().getClient() == null || !getClient().getClient().isConnected()) {
                    log.info("【emqx重新连接】");
                    getClient().reconnection();
                    log.info("【emqx重新连接成功】");
                }
            } catch (Exception e) {
                log.error("【emqx重连失败！】");
            }
        }
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
        Map<String, MqttBaseAcceptHandle> tokenServiceMap = SpringUtil.getBeansOfType(MqttBaseAcceptHandle.class);
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
            MqttMessage message = token.getMessage();
            byte[] payload = message.getPayload();
            String s = new String(payload, StandardCharsets.UTF_8);
            log.info("【消息内容】:{}", s);
        } catch (Exception e) {
            log.error("MqttAcceptCallback deliveryComplete error,message:", e);
        }
    }

    /**
     * 连接emq服务器后触发
     */
    @Override
    public void connectComplete(boolean b, String s) {
        log.info("============================= 客户端【{}】连接成功！=============================", getClient().getClient().getClientId());
        // 订阅所有接收主题
        if (CollUtil.isEmpty(getProperties().getAcceptTopics())) {
            return;
        }
        String[] topics = getProperties().getAcceptTopics().stream().map(MqttProperties.TopicSocketInfo::getTopic).filter(StrUtil::isNotBlank).toArray(String[]::new);
        int[] qos = getProperties().getAcceptTopics().stream().filter(item -> StrUtil.isNotBlank(item.getTopic())).map(item -> Optional.ofNullable(item.getQos()).orElse(0)).mapToInt(Integer::valueOf).toArray();
        getClient().subscribe(topics, qos);
    }
}
