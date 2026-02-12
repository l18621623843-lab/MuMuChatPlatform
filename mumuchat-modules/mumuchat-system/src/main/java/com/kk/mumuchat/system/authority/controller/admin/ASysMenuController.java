package com.kk.mumuchat.system.authority.controller.admin;

import com.kk.mumuchat.common.core.utils.TreeUtil;
import com.kk.mumuchat.common.core.utils.core.CollUtil;
import com.kk.mumuchat.common.core.utils.core.StrUtil;
import com.kk.mumuchat.common.core.web.result.AjaxResult;
import com.kk.mumuchat.common.core.web.validate.V_A;
import com.kk.mumuchat.common.core.web.validate.V_E;
import com.kk.mumuchat.common.log.annotation.Log;
import com.kk.mumuchat.common.log.enums.BusinessType;
import com.kk.mumuchat.common.security.annotation.AdminAuth;
import com.kk.mumuchat.common.security.service.TokenUserService;
import com.kk.mumuchat.common.security.utils.SecurityUserUtils;
import com.kk.mumuchat.system.api.authority.domain.dto.SysMenuDto;
import com.kk.mumuchat.system.api.authority.domain.query.SysMenuQuery;
import com.kk.mumuchat.system.api.model.DataScope;
import com.kk.mumuchat.system.authority.controller.base.BSysMenuController;
import com.kk.mumuchat.system.utils.V2.MRouteUtils;
import com.kk.mumuchat.system.utils.V5.RouteUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 系统服务|权限模块|菜单管理|管理端 业务处理
 *
 * @author xueyi
 */
@AdminAuth
@RestController
@RequestMapping("/admin/menu")
public class ASysMenuController extends BSysMenuController {

    @Resource
    private TokenUserService tokenService;

    @GetMapping("/getRouters")
    @Operation(summary = "获取路由信息")
    public AjaxResult getRouters(@RequestParam Long moduleId, @RequestParam(required = false) String version) {
        String cacheKey = StrUtil.format("{}-{}", moduleId, version);
        Map<String, Object> menuMap = Optional.ofNullable(tokenService.getMenuRoute()).orElseGet(HashMap::new);
        Object routeTree = Optional.ofNullable(menuMap.get(cacheKey))
                .orElseGet(() -> {
                    DataScope dataScope = SecurityUserUtils.getDataScope();
                    if (CollUtil.isNotEmpty(dataScope.getMenuIds())) {
                        List<SysMenuDto> menus = baseService.getRoutes(moduleId, version, dataScope.getMenuIds());
                        // 2.0版本默认未传版本号
                        if(StrUtil.isBlank(version)) {
                            menuMap.put(cacheKey, MRouteUtils.buildMenus(TreeUtil.buildTree(menus)));
                        } else {
                            menuMap.put(cacheKey, RouteUtil.buildMenus(TreeUtil.buildTree(menus)));
                        }
                    } else {
                        menuMap.put(cacheKey, new ArrayList<>());
                    }
                    tokenService.setMenuRoute(menuMap);
                    return menuMap.get(cacheKey);
                });
        return success(routeTree);
    }

    @GetMapping("/getModuleRouteURL")
    @Operation(summary = "获取当前用户模块路由映射")
    public AjaxResult getModuleRouteURL(@RequestParam(required = false) String version) {
        List<SysMenuDto> menuList = baseService.selectListToCurrentLoginUser(version);
        return success(RouteUtil.buildMenuRouteMap(menuList));
    }

    @Override
    @GetMapping("/list")
    @Operation(summary = "获取菜单列表")
    @PreAuthorize("@ss.hasAuthority(@Auth.SYS_MENU_LIST)")
    public AjaxResult list(SysMenuQuery menu) {
        return super.list(menu);
    }

    @Override
    @GetMapping(value = "/{id}")
    @Operation(summary = "获取菜单详细")
    @PreAuthorize("@ss.hasAuthority(@Auth.SYS_MENU_SINGLE)")
    public AjaxResult getInfo(@PathVariable Serializable id) {
        return success(baseService.selectInfoById(id));
    }

    @Override
    @PostMapping
    @Operation(summary = "菜单新增")
    @PreAuthorize("@ss.hasAuthority(@Auth.SYS_MENU_ADD)")
    @Log(title = "菜单管理", businessType = BusinessType.INSERT)
    public AjaxResult add(@Validated({V_A.class}) @RequestBody SysMenuDto menu) {
        return super.add(menu);
    }

    @Override
    @PutMapping
    @Operation(summary = "菜单修改")
    @PreAuthorize("@ss.hasAuthority(@Auth.SYS_MENU_EDIT)")
    @Log(title = "菜单管理", businessType = BusinessType.UPDATE)
    public AjaxResult edit(@Validated({V_E.class}) @RequestBody SysMenuDto menu) {
        return super.edit(menu);
    }

    @Override
    @PutMapping("/status")
    @Operation(summary = "菜单修改状态")
    @PreAuthorize("@ss.hasAnyAuthority(@Auth.SYS_MENU_EDIT, @Auth.SYS_MENU_ES)")
    @Log(title = "菜单管理", businessType = BusinessType.UPDATE_STATUS)
    public AjaxResult editStatus(@RequestBody SysMenuDto menu) {
        return super.editStatus(menu);
    }

    @Override
    @Operation(summary = "菜单批量删除")
    @DeleteMapping("/batch/{idList}")
    @PreAuthorize("@ss.hasAuthority(@Auth.SYS_MENU_DEL)")
    @Log(title = "菜单管理", businessType = BusinessType.DELETE)
    public AjaxResult batchRemove(@PathVariable List<Long> idList) {
        return super.batchRemove(idList);
    }

}
