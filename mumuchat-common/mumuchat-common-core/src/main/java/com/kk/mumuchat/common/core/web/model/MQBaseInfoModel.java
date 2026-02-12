package com.kk.mumuchat.common.core.web.model;

import com.kk.mumuchat.common.core.context.SecurityContextHolder;
import com.kk.mumuchat.common.core.utils.core.NumberUtil;
import com.kk.mumuchat.common.core.utils.core.ObjectUtil;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Optional;

/**
 * MQ消息基础信息 数据传输对象
 *
 * @author xueyi
 */
@Data
public class MQBaseInfoModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 重发次数 */
    private Integer doTimes = NumberUtil.One;

    /** 发送时间 */
    private Long sentTime;

    /** 基础信息 */
    protected BaseInfo baseInfo;

    /**
     * 注册信息
     */
    @Data
    public static class BaseInfo implements Serializable {

        @Serial
        private static final long serialVersionUID = 1L;

        /** 会员Id */
        private Long memberId;

        /** 企业Id */
        private Long enterpriseId;

        public BaseInfo() {
            this.memberId = SecurityContextHolder.getUserId();
            this.enterpriseId = SecurityContextHolder.getEnterpriseId();
        }
    }

    public MQBaseInfoModel() {
        this.baseInfo = new BaseInfo();
        this.setSentTime(System.currentTimeMillis());
    }

    public void refreshContextInfo() {
        Optional.ofNullable(this.baseInfo).ifPresent(info -> {
            Optional.ofNullable(info.getEnterpriseId()).filter(ObjectUtil::isNotNull).map(Object::toString).ifPresent(SecurityContextHolder::setEnterpriseId);
            Optional.ofNullable(info.getMemberId()).filter(ObjectUtil::isNotNull).map(Object::toString).ifPresent(SecurityContextHolder::setUserId);
        });
    }

    public void setContextInfo() {
        baseInfo.setMemberId(SecurityContextHolder.getUserId());
        baseInfo.setEnterpriseId(SecurityContextHolder.getEnterpriseId());
    }
}
