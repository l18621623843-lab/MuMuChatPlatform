package com.kk.mumuchat.common.core.web.tenant.common;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.kk.mumuchat.common.core.web.entity.common.CBasisEntity;
import com.kk.mumuchat.common.core.web.serializer.EnterpriseSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * Basis 租户混合基类
 *
 * @author xueyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TCBasisEntity extends CBasisEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 租户Id */
    @JsonSerialize(using = EnterpriseSerializer.class)
    @TableField(value = "tenant_id", insertStrategy = FieldStrategy.NEVER, updateStrategy = FieldStrategy.NEVER)
    protected Long enterpriseId;

}
