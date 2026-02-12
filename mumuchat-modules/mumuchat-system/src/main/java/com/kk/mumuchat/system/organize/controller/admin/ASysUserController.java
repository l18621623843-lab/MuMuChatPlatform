package com.kk.mumuchat.system.organize.controller.admin;

import com.kk.mumuchat.common.core.web.result.AjaxResult;
import com.kk.mumuchat.common.core.web.validate.V_A;
import com.kk.mumuchat.common.core.web.validate.V_E;
import com.kk.mumuchat.common.log.annotation.Log;
import com.kk.mumuchat.common.log.enums.BusinessType;
import com.kk.mumuchat.common.security.annotation.AdminAuth;
import com.kk.mumuchat.common.security.service.TokenUserService;
import com.kk.mumuchat.common.security.utils.SecurityUserUtils;
import com.kk.mumuchat.system.api.model.DataScope;
import com.kk.mumuchat.system.api.model.LoginUser;
import com.kk.mumuchat.system.api.organize.domain.dto.SysUserDto;
import com.kk.mumuchat.system.api.organize.domain.query.SysUserQuery;
import com.kk.mumuchat.system.organize.controller.base.BSysUserController;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * 系统服务|组织模块|用户管理|管理端 业务处理
 *
 * @author xueyi
 */
@AdminAuth
@RestController
@RequestMapping("/admin/user")
public class ASysUserController extends BSysUserController {

    @Resource
    private TokenUserService tokenService;

    @GetMapping("/getInfo")
    @Operation(summary = "获取用户信息")
    public AjaxResult getInfo() {
        LoginUser loginUser = tokenService.getLoginUser();
        baseService.userDesensitized(loginUser.getUser());
        HashMap<String, Object> map = new HashMap<>();
        map.put("enterprise", loginUser.getEnterprise());
        map.put("user", loginUser.getUser());
        DataScope dataScope = tokenService.getDataScope();
        map.put("roles", dataScope.getRoles());
        map.put("permissions", dataScope.getPermissions());
        map.put("routes", tokenService.getRouteURL());
        return success(map);
    }

    @Override
    @GetMapping("/list")
    @Operation(summary = "查询用户列表")
    @PreAuthorize("@ss.hasAuthority(@Auth.SYS_USER_LIST)")
    public AjaxResult list(SysUserQuery user) {
        startPage();
        List<SysUserDto> list = baseService.selectListScope(user);
        list.forEach(item -> baseService.userDesensitized(item));
        return getDataTable(list);
    }

    @Override
    @GetMapping(value = "/{id}")
    @Operation(summary = "查询用户详细")
    @PreAuthorize("@ss.hasAuthority(@Auth.SYS_USER_SINGLE)")
    public AjaxResult getInfo(@PathVariable Serializable id) {
        return super.getInfo(id);
    }

    @GetMapping(value = "/auth")
    @Operation(summary = "查询用户信息|含角色组")
    @PreAuthorize("@ss.hasAuthority(@Auth.SYS_USER_AUTH)")
    public AjaxResult getRoleAuth(@RequestParam Long id) {
        return success(baseService.selectUserRoleById(id));
    }

    @Override
    @PostMapping("/export")
    @Operation(summary = "用户导出")
    @PreAuthorize("@ss.hasAuthority(@Auth.SYS_USER_EXPORT)")
    @Log(title = "用户管理", businessType = BusinessType.EXPORT)
    public void export(HttpServletResponse response, SysUserQuery user) {
        super.export(response, user);
    }

    @Override
    @PostMapping
    @Operation(summary = "用户新增")
    @PreAuthorize("@ss.hasAuthority(@Auth.SYS_USER_ADD)")
    @Log(title = "用户管理", businessType = BusinessType.INSERT)
    public AjaxResult add(@Validated({V_A.class}) @RequestBody SysUserDto user) {
        return super.add(user);
    }

    @Override
    @PutMapping
    @Operation(summary = "用户修改")
    @PreAuthorize("@ss.hasAuthority(@Auth.SYS_USER_EDIT)")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    public AjaxResult edit(@Validated({V_E.class}) @RequestBody SysUserDto user) {
        return super.edit(user);
    }

    @PutMapping(value = "/auth")
    @Operation(summary = "修改用户关联的角色Id集")
    @PreAuthorize("@ss.hasAuthority(@Auth.SYS_USER_AUTH)")
    public AjaxResult editRoleAuth(@RequestBody SysUserDto user) {
        baseService.editUserRole(user);
        return success();
    }

    @Override
    @PutMapping("/status")
    @Operation(summary = "用户修改状态")
    @PreAuthorize("@ss.hasAnyAuthority(@Auth.SYS_USER_EDIT, @Auth.SYS_USER_ES)")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE_STATUS)
    public AjaxResult editStatus(@RequestBody SysUserDto user) {
        return super.editStatus(user);
    }

    @Override
    @Operation(summary = "用户批量删除")
    @DeleteMapping("/batch/{idList}")
    @PreAuthorize("@ss.hasAuthority(@Auth.SYS_USER_DEL)")
    @Log(title = "用户管理", businessType = BusinessType.DELETE)
    public AjaxResult batchRemove(@PathVariable List<Long> idList) {
        return super.batchRemove(idList);
    }

    @PutMapping("/resetPwd")
    @Operation(summary = "重置密码")
    @PreAuthorize("@ss.hasAuthority(@Auth.SYS_USER_RES_PWD)")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    public AjaxResult resetPassword(@RequestBody SysUserDto user) {
        adminValidated(user.getId());
        return toAjax(baseService.resetUserPassword(user.getId(), SecurityUserUtils.encryptPassword(user.getPassword())));
    }
}
