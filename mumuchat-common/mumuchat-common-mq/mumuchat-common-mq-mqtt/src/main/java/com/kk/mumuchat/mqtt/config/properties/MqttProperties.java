package com.kk.mumuchat.mqtt.config.properties;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * MQTT配置对象
 *
 * @author mumuchat
 */
@Data
@Configuration
@ConfigurationProperties("xueyi.mq.mqtt")
public class MqttProperties {

    /** 用户名 */
    private String username;

    /** 密码 */
    private String password;

    /** 连接地址 */
    private String hostUrl;

    /** 客户端Id，同一台服务器下，不允许出现重复的客户端Id */
    private String clientId;

    /** 接收订阅主题 */
    private List<TopicSocketInfo> acceptTopics;

    /** 前缀标识 */
    private String prefix = StrUtil.EMPTY;

    /** 环境标识 */
    private String evn = StrUtil.EMPTY;

    /** 超时时间 */
    private int timeout = 30;

    /**
     * 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端
     * 发送个消息判断客户端是否在线，但这个方法并没有重连的机制
     */
    private int keepAlive = 60;

    /**
     * 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连
     * 接记录，这里设置为true表示每次连接到服务器都以新的身份连接
     */
    private Boolean cleanSession = Boolean.TRUE;

    /** 是否断线重连 */
    private Boolean reconnect = Boolean.TRUE;

    /** 启动的时候是否开启mqtt */
    private Boolean isOpen = Boolean.TRUE;

    /** 连接方式 */
    private Integer qos = 1;

    /** 获取服务器发送主题，格式：server/${env}/report/${topic} */
    public String getUniqueClientId() {
        return StrUtil.isNotBlank(clientId)
                ? clientId + "-" + IdUtil.fastSimpleUUID()
                : IdUtil.fastSimpleUUID();
    }

    /** 获取服务器发送主题，格式：server/${env}/report/${topic} */
    public String getServerTopic(String topic) {
        return StrUtil.format("{}{}", getEvnPrefix(), topic);
    }

    /**
     * 获取系统环境前缀
     *
     * @return 结果
     */
    public String getEvnPrefix() {
        String prefix = StrUtil.isNotBlank(this.prefix) ? StrUtil.format("{}/", this.prefix) : StrUtil.EMPTY;
        String evn = StrUtil.isNotBlank(this.evn) ? StrUtil.format("{}/", this.evn) : StrUtil.EMPTY;
        return StrUtil.format("{}{}", prefix, evn);
    }

    /** 主题订阅配置 */
    @Data
    public static class TopicSocketInfo {

        /** 订阅主题 */
        private String topic;

        /** 服务质量等级 */
        private Integer qos;
    }
}
