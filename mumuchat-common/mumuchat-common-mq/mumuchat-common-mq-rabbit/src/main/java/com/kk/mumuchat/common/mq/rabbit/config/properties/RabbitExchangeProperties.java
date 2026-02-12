package com.kk.mumuchat.common.mq.rabbit.config.properties;

import com.kk.mumuchat.common.core.utils.core.StrUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

/**
 * 安全配置
 *
 * @author xueyi
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "xueyi.mq.rabbit")
public class RabbitExchangeProperties {

    /** 启动的时候是否开启rabbit */
    private Boolean isOpen = Boolean.TRUE;

    /** 前缀标识 */
    private String prefix = StrUtil.EMPTY;

    /** 环境标识 */
    private String evn = StrUtil.EMPTY;

    /** 交换机配置 */
    private List<ExchangeInfo> exchangeInfos;

    /**
     * 交换机配置
     */
    @Data
    @NoArgsConstructor
    public static class ExchangeInfo {

        /** 地址（自定义） */
        private String host;

        /** 端口（自定义） */
        private Integer port;

        /** 账号（自定义） */
        private String username;

        /** 密码（自定义） */
        private String password;

        /** 交换机类型（direct直连交换机 topic主题交换机 fanout扇出交换机 headers头交换机 x-delayed-message延时消息交换机） */
        private String type;

        /** 交换机名称 */
        private String name;

        /** 是否持久化 */
        private Boolean durable = Boolean.TRUE;

        /** 该队列是否只能被当前连接使用，连接关闭则队列删除。 优先级高于durable */
        private Boolean exclusive = Boolean.FALSE;

        /** 是否自动删除队列（没有生产者或消费者使用此队列，则此队列自动删除） */
        private Boolean autoDelete = Boolean.FALSE;

        /** 自定义参数 */
        private Map<String, Object> params;

        /** 队列配置 */
        private List<QueueInfo> queueInfos;

        /**
         * 队列配置
         */
        @Data
        @NoArgsConstructor
        public static class QueueInfo {

            /** 路由键 */
            private String routingKey;

            /** 路由键集合 */
            private List<String> routingKeyList;

            /** 队列名称 */
            private String name;

            /** 是否持久化 */
            private Boolean durable = Boolean.TRUE;

            /** 该队列是否只能被当前连接使用，连接关闭则队列删除。 优先级高于durable */
            private Boolean exclusive = Boolean.FALSE;

            /** 是否自动删除队列（没有生产者或消费者使用此队列，则此队列自动删除） */
            private Boolean autoDelete = Boolean.FALSE;

            /** 自定义参数 */
            private Map<String, Object> params;

        }
    }

    /**
     * 获取系统环境前缀
     *
     * @return 结果
     */
    public String getEvnPrefix() {
        String prefix = StrUtil.isNotBlank(this.prefix) ? StrUtil.format("{}.", this.prefix) : StrUtil.EMPTY;
        String evn = StrUtil.isNotBlank(this.evn) ? StrUtil.format("{}.", this.evn) : StrUtil.EMPTY;
        return StrUtil.format("{}{}", prefix, evn);
    }
}