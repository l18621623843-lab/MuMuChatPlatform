package com.kk.mumuchat.common.core.web.model;

import com.alibaba.fastjson2.JSONObject;
import com.kk.mumuchat.common.core.constant.basic.TenantConstants;
import com.kk.mumuchat.common.core.web.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 源策略 基础数据对象
 *
 * @author mumuchat
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysSource extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 源策略组Id */
    protected Long strategyId;

    /** 数据源Id */
    protected  Long sourceId;

    /** 主数据源 */
    protected String master;

    /** 策略组类型配置信息 */
    protected JSONObject sourceTypeInfo;

    public String getSourceSlave(TenantConstants.StrategyType strategyType) {
        return switch (strategyType) {
            case DEFAULT -> master;
            default -> sourceTypeInfo.getString(strategyType.getCode());
        };
    }
}