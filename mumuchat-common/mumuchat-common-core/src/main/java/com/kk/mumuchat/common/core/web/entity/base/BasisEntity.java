package com.kk.mumuchat.common.core.web.entity.base;

import com.baomidou.mybatisplus.annotation.OrderBy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kk.mumuchat.common.core.constant.basic.BaseConstants;
import com.kk.mumuchat.common.core.utils.core.ObjectUtil;
import com.kk.mumuchat.common.core.web.validate.V_E;
import com.kk.mumuchat.common.core.web.validate.V_ID;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * Basis 基类
 *
 * @author mumuchat
 */
@Data
public class BasisEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** Id */
    @TableId
    @OrderBy
    @NotNull(message = "id不能为空", groups = {V_E.class, V_ID.class})
    protected Long id;

    /** 显示顺序 */
    @TableField(exist = false)
    protected Integer sort;

    /** 数据源名称 */
    @JsonIgnore
    @TableField(exist = false)
    protected String sourceName;

    /** 操作类型 */
    @JsonIgnore
    @TableField(exist = false)
    protected BaseConstants.Operate operate;

    /** 导出编码 */
    @TableField(exist = false)
    protected String exCode;

    /** 数据限制条数 */
    @JsonIgnore
    @TableField(exist = false)
    protected Integer limitNum;

    @JsonIgnore
    public String getIdStr() {
        return ObjectUtil.isNotNull(id) ? id.toString() : null;
    }

    @JsonIgnore
    @SuppressWarnings("all")
    public void initId() {
        id = null;
    }

    /**
     * 初始化操作类型
     *
     * @param operate 操作类型
     */
    @JsonIgnore
    public void initOperate(BaseConstants.Operate operate) {
        if (ObjectUtil.isNull(this.operate))
            this.operate = operate;
    }
}
