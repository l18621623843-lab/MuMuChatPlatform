package com.kk.mumuchat.common.mq.rabbit.contant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * RabbitMQ 通用常量
 *
 * @author mumuchat
 */
public class RabbitConstants {

    /** 交换机类型 */
    @Getter
    @AllArgsConstructor
    public enum ExchangeType {

        DIRECT("direct", "直连交换机"),
        FANOUT("fanout", "扇出交换机"),
        TOPIC("topic", "主题交换机"),
        HEADERS("headers", "头交换机"),
        // 延时交换机必须给RabbitMQ安装延时插件
        DELAYED_MSG("x-delayed-message", "延时消息交换机");

        private final String code;
        private final String info;

    }
}
