package com.kk.mumuchat.common.mq.rabbit.domain;

import com.kk.mumuchat.common.core.web.model.MQBaseInfoModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * MQ消息信息体
 *
 * @author mumuchat
 */
@Data
@NoArgsConstructor
public class MqMsgInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 交换机名称 */
    private String exchangeName;

    /** 路由键 */
    private String routingKey;

    /** 消息体 */
    private Object msgInfo;

    /** 延时时间（时间单位/默认秒） */
    private Long delayTime;

    /** 延时时间（毫秒） */
    private TimeUnit timeUnit;

    public <T extends MQBaseInfoModel> MqMsgInfo(String exchangeName, String routingKey, T msgInfo) {
        this(exchangeName, routingKey, msgInfo, null, null);
    }

    public <T extends MQBaseInfoModel> MqMsgInfo(String exchangeName, String routingKey, T msgInfo, Long delayTime, TimeUnit timeUnit) {
        this.exchangeName = exchangeName;
        this.routingKey = routingKey;
        this.msgInfo = msgInfo;
        this.delayTime = delayTime;
        this.timeUnit = timeUnit;
    }

    public void setDelayTime(Long delayTime, TimeUnit timeUnit) {
        this.delayTime = delayTime;
        this.timeUnit = timeUnit;
    }
}
