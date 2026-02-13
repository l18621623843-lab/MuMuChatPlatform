package com.kk.mumuchat.system.authority.domain.merge;

import com.baomidou.mybatisplus.annotation.TableName;
import com.kk.mumuchat.common.core.web.tenant.base.TBasisEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;

/**
 * 系统服务|权限模块|角色-模块关联 持久化对象
 *
 * @author mumuchat
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("sys_role_module_merge")
public class SysRoleModuleMerge extends TBasisEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 角色Id */
    private Long roleId;

    /** 模块Id */
    private Long moduleId;

    public SysRoleModuleMerge(Long roleId, Long moduleId) {
        this.roleId = roleId;
        this.moduleId = moduleId;
    }

}
