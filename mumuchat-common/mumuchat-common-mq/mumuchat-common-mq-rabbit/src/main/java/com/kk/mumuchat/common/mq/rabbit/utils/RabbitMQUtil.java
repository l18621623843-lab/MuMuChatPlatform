package com.kk.mumuchat.common.mq.rabbit.utils;

import com.alibaba.fastjson2.JSONObject;
import com.rabbitmq.client.Channel;
import com.kk.mumuchat.common.core.utils.core.ObjectUtil;
import com.kk.mumuchat.common.core.web.model.MQBaseInfoModel;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageProperties;

/**
 * RabbitMQ 消息处理工具类
 *
 * @author xueyi
 */
@Slf4j
public class RabbitMQUtil {

    /**
     * 通用 MQ 消息处理工具方法
     *
     * @param msgInfo  解析后的消息体，包含业务数据
     * @param runnable 消息处理方法
     * @param channel  当前 MQ 的 Channel，用于手动确认或拒绝消息
     * @param message  消息属性，包含 deliveryTag 等元信息
     * @param logTitle 日志标题，用于区分不同业务场景的日志输出
     */
    @SneakyThrows
    public static <T> void handleMsg(T msgInfo, Runnable runnable, Channel channel, MessageProperties message, String logTitle) {
        try {
            log.info("\n【{}】MQ消息执行，\n执行内容：{}\n", logTitle, JSONObject.toJSONString(msgInfo));
            if (ObjectUtil.isNotNull(runnable)) {
                runnable.run();
            }
            channel.basicAck(message.getDeliveryTag(), false);
        } catch (Exception e) {
            log.error("【{}】MQ消息处理失败：{}，报错内容：", logTitle, e.getMessage(), e);
            channel.basicReject(message.getDeliveryTag(), false);
        }
    }

    /**
     * 通用 MQ 消息处理工具方法
     *
     * @param msgInfo  解析后的消息体，包含业务数据
     * @param runnable 消息处理方法
     * @param channel  当前 MQ 的 Channel，用于手动确认或拒绝消息
     * @param message  消息属性，包含 deliveryTag 等元信息
     * @param logTitle 日志标题，用于区分不同业务场景的日志输出
     */
    @SneakyThrows
    public static <T extends MQBaseInfoModel> void handleMsg(T msgInfo, Runnable runnable, Channel channel, MessageProperties message, String logTitle) {
        try {
            log.info("\n【{}】MQ消息执行，\n执行次数：{}，\n执行内容：{}\n", logTitle, msgInfo.getDoTimes(), JSONObject.toJSONString(msgInfo));
            msgInfo.refreshContextInfo();
            if (ObjectUtil.isNotNull(runnable)) {
                runnable.run();
            }
            channel.basicAck(message.getDeliveryTag(), false);
        } catch (Exception e) {
            log.error("【{}】MQ消息处理失败：{}，报错内容：", logTitle, e.getMessage(), e);
            channel.basicReject(message.getDeliveryTag(), false);
        }
    }
}
