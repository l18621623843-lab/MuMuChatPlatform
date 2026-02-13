package com.kk.mumuchat.system.api.organize.domain.dto;

import com.kk.mumuchat.common.core.constant.basic.SecurityConstants;
import com.kk.mumuchat.common.core.utils.core.StrUtil;
import com.kk.mumuchat.common.core.web.validate.V_A_E;
import com.kk.mumuchat.system.api.authority.domain.dto.SysRoleDto;
import com.kk.mumuchat.system.api.organize.domain.po.SysUserPo;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.util.List;

/**
 * 系统服务|组织模块|用户 数据传输对象
 *
 * @author mumuchat
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserDto extends SysUserPo {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 岗位对象 */
    private List<SysPostDto> posts;

    /** 角色对象 */
    private List<SysRoleDto> roles;

    /** 岗位组 */
    @NotEmpty(message = "归属岗位不能为空", groups = {V_A_E.class})
    private Long[] postIds;

    /** 角色组 */
    private Long[] roleIds;

    public boolean isNotAdmin() {
        return !isAdmin(this.getUserType());
    }

    public boolean isAdmin() {
        return isAdmin(this.getUserType());
    }

    public static boolean isNotAdmin(String userType) {
        return !isAdmin(userType);
    }

    public static boolean isAdmin(String userType) {
        return StrUtil.equals(SecurityConstants.UserType.ADMIN.getCode(), userType);
    }

}
