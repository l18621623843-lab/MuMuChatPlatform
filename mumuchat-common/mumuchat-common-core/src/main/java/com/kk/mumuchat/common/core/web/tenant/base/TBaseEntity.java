package com.kk.mumuchat.common.core.web.tenant.base;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.kk.mumuchat.common.core.web.entity.base.BaseEntity;
import com.kk.mumuchat.common.core.web.serializer.EnterpriseSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * Base 租户基类
 *
 * @author mumuchat
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TBaseEntity extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 租户Id */
    @JsonSerialize(using = EnterpriseSerializer.class)
    @TableField(value = "tenant_id", insertStrategy = FieldStrategy.NEVER, updateStrategy = FieldStrategy.NEVER)
    protected Long enterpriseId;

}
