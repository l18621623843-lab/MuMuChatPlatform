package com.kk.mumuchat.system.authority.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.kk.mumuchat.common.core.constant.basic.BaseConstants;
import com.kk.mumuchat.common.core.constant.basic.DictConstants;
import com.kk.mumuchat.common.core.constant.basic.SecurityConstants;
import com.kk.mumuchat.common.core.utils.core.CollUtil;
import com.kk.mumuchat.common.core.utils.core.ObjectUtil;
import com.kk.mumuchat.common.core.utils.core.StrUtil;
import com.kk.mumuchat.common.web.entity.manager.impl.TreeManagerImpl;
import com.kk.mumuchat.system.api.authority.constant.AuthorityConstants;
import com.kk.mumuchat.system.api.authority.domain.dto.SysMenuDto;
import com.kk.mumuchat.system.api.authority.domain.po.SysMenuPo;
import com.kk.mumuchat.system.api.authority.domain.query.SysMenuQuery;
import com.kk.mumuchat.system.api.organize.domain.dto.SysUserDto;
import com.kk.mumuchat.system.authority.domain.merge.SysAuthGroupMenuMerge;
import com.kk.mumuchat.system.authority.domain.merge.SysRoleMenuMerge;
import com.kk.mumuchat.system.authority.domain.model.SysMenuConverter;
import com.kk.mumuchat.system.authority.manager.ISysMenuManager;
import com.kk.mumuchat.system.authority.mapper.SysMenuMapper;
import com.kk.mumuchat.system.authority.mapper.merge.SysAuthGroupMenuMergeMapper;
import com.kk.mumuchat.system.authority.mapper.merge.SysRoleMenuMergeMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 系统服务|权限模块|菜单管理 数据封装层处理
 *
 * @author mumuchat
 */
@Component
public class SysMenuManagerImpl extends TreeManagerImpl<SysMenuQuery, SysMenuDto, SysMenuPo, SysMenuMapper, SysMenuConverter> implements ISysMenuManager {

    @Resource
    private SysAuthGroupMenuMergeMapper authGroupMenuMergeMapper;

    @Resource
    private SysRoleMenuMergeMapper roleMenuMergeMapper;

    @Override
    public List<SysMenuDto> selectCommonList() {
        List<SysMenuPo> menuList = baseMapper.selectList(Wrappers.<SysMenuPo>lambdaQuery()
                .eq(SysMenuPo::getIsCommon, DictConstants.DicCommonPrivate.COMMON.getCode())
                .eq(SysMenuPo::getStatus, BaseConstants.Status.NORMAL.getCode()));
        return mapperDto(menuList);
    }

    @Override
    public List<SysMenuDto> selectEnterpriseList(Set<Long> authGroupIds, Set<Long> roleIds, String isLessor, String userType) {
        List<SysMenuPo> menuList;
        // 租管租户 && 超级管理员
        if (SecurityConstants.TenantType.isLessor(isLessor) && SysUserDto.isAdmin(userType)) {
            menuList = baseMapper.selectList(Wrappers.<SysMenuPo>lambdaQuery()
                    .eq(SysMenuPo::getStatus, BaseConstants.Status.NORMAL.getCode()));
        } else {
            Set<Long> authGroupMenuIds;
            if (SecurityConstants.TenantType.isNotLessor(isLessor)) {
                if (CollUtil.isEmpty(authGroupIds)) {
                    return new ArrayList<>();
                }
                // 获取租户授权的公共菜单Ids
                List<SysAuthGroupMenuMerge> authGroupMenuMerges = authGroupMenuMergeMapper.selectList(Wrappers.<SysAuthGroupMenuMerge>lambdaQuery()
                        .in(SysAuthGroupMenuMerge::getAuthGroupId, authGroupIds));
                if (CollUtil.isEmpty(authGroupMenuMerges)) {
                    return new ArrayList<>();
                }
                authGroupMenuIds = authGroupMenuMerges.stream().map(SysAuthGroupMenuMerge::getMenuId).collect(Collectors.toSet());
            } else {
                authGroupMenuIds = new HashSet<>();
            }
            // 普通租户 && 超级管理员
            if (SysUserDto.isAdmin(userType)) {
                menuList = baseMapper.selectList(Wrappers.<SysMenuPo>lambdaQuery()
                        .eq(SysMenuPo::getStatus, BaseConstants.Status.NORMAL.getCode())
                        .eq(CollUtil.isEmpty(authGroupMenuIds), SysMenuPo::getIsCommon, DictConstants.DicCommonPrivate.PRIVATE.getCode())
                        .and(CollUtil.isNotEmpty(authGroupMenuIds), a -> a.eq(SysMenuPo::getIsCommon, DictConstants.DicCommonPrivate.PRIVATE.getCode())
                                .or().in(SysMenuPo::getId, authGroupMenuIds)));
            } else {
                if (CollUtil.isEmpty(roleIds)) {
                    return new ArrayList<>();
                }
                List<SysRoleMenuMerge> roleMenuMerges = roleMenuMergeMapper.selectList(Wrappers.<SysRoleMenuMerge>lambdaQuery()
                        .in(SysRoleMenuMerge::getRoleId, roleIds));
                if (CollUtil.isEmpty(roleMenuMerges)) {
                    return new ArrayList<>();
                }
                Set<Long> roleMenuIds = roleMenuMerges.stream().map(SysRoleMenuMerge::getMenuId).collect(Collectors.toSet());
                // 租管租户 && 普通用户
                if (SecurityConstants.TenantType.isLessor(isLessor)) {
                    menuList = baseMapper.selectList(Wrappers.<SysMenuPo>lambdaQuery()
                            .eq(SysMenuPo::getStatus, BaseConstants.Status.NORMAL.getCode())
                            .in(SysMenuPo::getId, roleMenuIds));
                }
                // 普通租户 && 普通用户
                else {
                    menuList = baseMapper.selectList(Wrappers.<SysMenuPo>lambdaQuery()
                            .eq(SysMenuPo::getStatus, BaseConstants.Status.NORMAL.getCode())
                            .in(SysMenuPo::getId, roleMenuIds)
                            .eq(CollUtil.isEmpty(authGroupMenuIds), SysMenuPo::getIsCommon, DictConstants.DicCommonPrivate.PRIVATE.getCode())
                            .and(CollUtil.isNotEmpty(authGroupMenuIds), a -> a.eq(SysMenuPo::getIsCommon, DictConstants.DicCommonPrivate.PRIVATE.getCode())
                                    .or().in(SysMenuPo::getId, authGroupMenuIds)));
                }
            }
        }
        return mapperDto(menuList);
    }

    @Override
    public List<SysMenuDto> getRoutes(Long moduleId, String version, Collection<Long> menuIds) {
        if (ObjectUtil.isNull(moduleId) || CollUtil.isEmpty(menuIds)) {
            return new ArrayList<>();
        } else {
            return mapperDto(baseMapper.selectList(Wrappers.<SysMenuPo>lambdaQuery()
                    .in(SysMenuPo::getId, menuIds)
                    // 指定版本则指定版本，反之则默认菜单
                    .eq(SysMenuPo::getVersion, StrUtil.getStrOrBlankElse(version, StrUtil.EMPTY))
                    .eq(SysMenuPo::getModuleId, moduleId)
                    .in(SysMenuPo::getMenuType, AuthorityConstants.MenuType.DIR.getCode(),
                            AuthorityConstants.MenuType.MENU.getCode(),
                            AuthorityConstants.MenuType.DETAILS.getCode())));
        }
    }

    @Override
    public List<SysMenuDto> selectMenuListByIdsWithVersion(Collection<Long> menuIds, String version) {
        String queryVersion = StrUtil.getStrOrBlankElse(version, StrUtil.EMPTY);
        List<String> queryMenuTypes = new ArrayList<>() {{
            add(AuthorityConstants.MenuType.DIR.getCode());
            add(AuthorityConstants.MenuType.MENU.getCode());
            add(AuthorityConstants.MenuType.DETAILS.getCode());
        }};
        return CollUtil.selectListToBatch(menuIds, batchIds ->
                mapperDto(baseMapper.selectList(Wrappers.<SysMenuPo>lambdaQuery()
                        .in(SysMenuPo::getId, batchIds)
                        .eq(SysMenuPo::getVersion, queryVersion)
                        .in(SysMenuPo::getMenuType, queryMenuTypes))
                ));
    }

    /**
     * 查询条件构造|列表查询
     *
     * @param query 数据查询对象
     * @return 条件构造器
     */
    protected LambdaQueryWrapper<SysMenuPo> selectListQuery(SysMenuQuery query) {
        return super.selectListQuery(query)
                .func(i -> {
                    if (StrUtil.isNotBlank(query.getMenuTypeLimit())) {
                        switch (AuthorityConstants.MenuType.getByCode(query.getMenuTypeLimit())) {
                            case BUTTON, DETAILS -> i
                                    .and(ai -> ai.eq(SysMenuPo::getMenuType, AuthorityConstants.MenuType.MENU.getCode())
                                            .or().eq(SysMenuPo::getMenuType, AuthorityConstants.MenuType.DIR.getCode()));
                            case MENU, DIR -> i
                                    .eq(SysMenuPo::getMenuType, AuthorityConstants.MenuType.DIR.getCode());
                        }
                    }
                });
    }

    /**
     * 修改条件构造|树子数据修改
     *
     * @param menu 数据传输对象
     * @return 条件构造器
     */
    @Override
    protected LambdaUpdateWrapper<SysMenuPo> updateChildrenWrapper(SysMenuDto menu) {
        return Wrappers.<SysMenuPo>lambdaUpdate().set(SysMenuPo::getModuleId, menu.getModuleId());
    }
}
