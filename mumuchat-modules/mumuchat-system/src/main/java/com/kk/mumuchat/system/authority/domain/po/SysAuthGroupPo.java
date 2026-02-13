package com.kk.mumuchat.system.authority.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.kk.mumuchat.common.core.annotation.Excel;
import com.kk.mumuchat.common.core.web.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;


/**
 * 系统服务|权限模块|企业权限组 持久化对象
 *
 * @author mumuchat
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_auth_group")
public class SysAuthGroupPo extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 权限组编码 */
    @Excel(name = "权限组编码")
    protected String code;

    /** 权限组权限字符串 */
    @Excel(name = "权限组权限字符串")
    protected String authKey;

}