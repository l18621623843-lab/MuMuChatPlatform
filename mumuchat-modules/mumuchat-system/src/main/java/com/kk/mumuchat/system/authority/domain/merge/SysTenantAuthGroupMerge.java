package com.kk.mumuchat.system.authority.domain.merge;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kk.mumuchat.common.core.web.tenant.base.TBasisEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;

/**
 * 系统服务|权限模块|企业和企业权限组关联 持久化对象
 *
 * @author mumuchat
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("sys_tenant_auth_group_merge")
public class SysTenantAuthGroupMerge extends TBasisEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /** id */
    @TableId
    protected Long id;

    /** 企业权限组Id */
    protected Long authGroupId;

    /** 企业Id */
    protected Long tenantId;

}