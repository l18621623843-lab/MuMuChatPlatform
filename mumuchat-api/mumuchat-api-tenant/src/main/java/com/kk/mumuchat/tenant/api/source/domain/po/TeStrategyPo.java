package com.kk.mumuchat.tenant.api.source.domain.po;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.Fastjson2TypeHandler;
import com.kk.mumuchat.common.core.web.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 租户服务 | 策略模块 | 源策略 持久化对象
 *
 * @author mumuchat
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "te_strategy", autoResultMap = true)
public class TeStrategyPo extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 数据源Id */
    @TableField(updateStrategy = FieldStrategy.NEVER)
    protected Long sourceId;

    /** 数据源编码 */
    @TableField(updateStrategy = FieldStrategy.NEVER)
    protected String sourceSlave;

    /** 策略组类型配置信息 */
    @TableField(typeHandler = Fastjson2TypeHandler.class)
    protected JSONObject sourceTypeInfo;

    /** 默认策略（Y是 N否） */
    @TableField(updateStrategy = FieldStrategy.NEVER)
    protected String isDefault;

}