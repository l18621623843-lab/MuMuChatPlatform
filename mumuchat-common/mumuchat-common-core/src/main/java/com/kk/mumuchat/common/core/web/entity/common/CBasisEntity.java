package com.kk.mumuchat.common.core.web.entity.common;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.kk.mumuchat.common.core.constant.basic.DictConstants;
import com.kk.mumuchat.common.core.web.entity.base.BasisEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * Base 混合基类
 *
 * @author xueyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CBasisEntity extends BasisEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 公共数据（0公共 1私有） */
    @TableField(updateStrategy = FieldStrategy.NEVER)
    protected String isCommon;

    /** 校验是否为公共数据 */
    public boolean isCommon() {
        return DictConstants.DicCommonPrivate.isCommon(getIsCommon());
    }

    /** 校验是否非公共数据 */
    public boolean isNotCommon() {
        return !isCommon();
    }
}
