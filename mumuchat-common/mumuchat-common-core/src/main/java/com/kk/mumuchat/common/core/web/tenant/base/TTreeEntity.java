package com.kk.mumuchat.common.core.web.tenant.base;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.kk.mumuchat.common.core.web.entity.base.TreeEntity;
import com.kk.mumuchat.common.core.web.serializer.EnterpriseSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * Tree 租户基类
 *
 * @param <D> Dto
 * @author mumuchat
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TTreeEntity<D> extends TreeEntity<D> {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 租户Id */
    @JsonSerialize(using = EnterpriseSerializer.class)
    @TableField(value = "tenant_id", insertStrategy = FieldStrategy.NEVER, updateStrategy = FieldStrategy.NEVER)
    protected Long enterpriseId;

}
