package com.kk.mumuchat.system.authority.controller.admin;

import com.kk.mumuchat.common.core.web.result.AjaxResult;
import com.kk.mumuchat.common.core.web.validate.V_A;
import com.kk.mumuchat.common.core.web.validate.V_E;
import com.kk.mumuchat.common.log.annotation.Log;
import com.kk.mumuchat.common.log.enums.BusinessType;
import com.kk.mumuchat.common.security.annotation.AdminAuth;
import com.kk.mumuchat.system.api.authority.domain.dto.SysRoleDto;
import com.kk.mumuchat.system.api.authority.domain.query.SysRoleQuery;
import com.kk.mumuchat.system.authority.controller.base.BSysRoleController;
import io.swagger.v3.oas.annotations.Operation;
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
import java.util.List;

/**
 * 系统服务|权限模块|角色管理|管理端 业务处理
 *
 * @author mumuchat
 */
@AdminAuth
@RestController
@RequestMapping("/admin/role")
public class ASysRoleController extends BSysRoleController {

    @Override
    @GetMapping("/list")
    @Operation(summary = "获取角色列表")
    @PreAuthorize("@ss.hasAnyAuthority(@Auth.SYS_ROLE_LIST, @Auth.SYS_DEPT_LIST, @Auth.SYS_POST_LIST, @Auth.SYS_USER_LIST)")
    public AjaxResult list(SysRoleQuery role) {
        return super.list(role);
    }

    @Override
    @GetMapping(value = "/{id}")
    @Operation(summary = "获取角色详细")
    @PreAuthorize("@ss.hasAuthority(@Auth.SYS_ROLE_SINGLE)")
    public AjaxResult getInfo(@PathVariable Serializable id) {
        return super.getInfo(id);
    }

    @GetMapping("/auth")
    @Operation(summary = "获取角色功能权限|叶子节点")
    @PreAuthorize("@ss.hasAuthority(@Auth.SYS_ROLE_AUTH)")
    public AjaxResult getRoleAuth(@RequestParam Long id) {
        return success(baseService.selectAuthById(id));
    }

    @GetMapping("/organize")
    @Operation(summary = "获取角色组织权限")
    @PreAuthorize("@ss.hasAuthority(@Auth.SYS_ROLE_AUTH)")
    public AjaxResult getRoleOrganize(@RequestParam Long id) {
        return success(baseService.selectDataById(id));
    }

    @Override
    @PostMapping
    @Operation(summary = "角色新增")
    @PreAuthorize("@ss.hasAuthority(@Auth.SYS_ROLE_ADD)")
    @Log(title = "角色管理", businessType = BusinessType.INSERT)
    public AjaxResult add(@Validated({V_A.class}) @RequestBody SysRoleDto role) {
        return super.add(role);
    }

    @Override
    @PutMapping
    @Operation(summary = "角色修改")
    @PreAuthorize("@ss.hasAuthority(@Auth.SYS_ROLE_EDIT)")
    @Log(title = "角色管理", businessType = BusinessType.UPDATE)
    public AjaxResult edit(@Validated({V_E.class}) @RequestBody SysRoleDto role) {
        return super.edit(role);
    }

    @PutMapping("/auth")
    @Operation(summary = "角色修改功能权限")
    @PreAuthorize("@ss.hasAuthority(@Auth.SYS_ROLE_AUTH)")
    @Log(title = "角色管理", businessType = BusinessType.AUTH)
    public AjaxResult editAuth(@RequestBody SysRoleDto role) {
        baseService.updateRoleAuth(role);
        return success();
    }

    @PutMapping("/organize")
    @Operation(summary = "角色修改组织权限")
    @PreAuthorize("@ss.hasAuthority(@Auth.SYS_ROLE_AUTH)")
    @Log(title = "角色管理", businessType = BusinessType.AUTH)
    public AjaxResult editOrganize(@RequestBody SysRoleDto role) {
        baseService.updateDataScope(role);
        return success();
    }

    @Override
    @PutMapping("/status")
    @Operation(summary = "角色修改状态")
    @PreAuthorize("@ss.hasAnyAuthority(@Auth.SYS_ROLE_EDIT, @Auth.SYS_ROLE_ES)")
    @Log(title = "角色管理", businessType = BusinessType.UPDATE_STATUS)
    public AjaxResult editStatus(@RequestBody SysRoleDto role) {
        return super.editStatus(role);
    }

    @Override
    @DeleteMapping("/batch/{idList}")
    @Operation(summary = "角色批量删除")
    @PreAuthorize("@ss.hasAuthority(@Auth.SYS_ROLE_DEL)")
    @Log(title = "角色管理", businessType = BusinessType.DELETE)
    public AjaxResult batchRemove(@PathVariable List<Long> idList) {
        return super.batchRemove(idList);
    }

}
