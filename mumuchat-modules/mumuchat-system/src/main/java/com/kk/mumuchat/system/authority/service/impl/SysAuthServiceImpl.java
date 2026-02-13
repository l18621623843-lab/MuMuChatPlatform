package com.kk.mumuchat.system.authority.service.impl;

import com.kk.mumuchat.common.core.constant.basic.BaseConstants;
import com.kk.mumuchat.common.core.utils.TreeUtil;
import com.kk.mumuchat.common.core.utils.core.IdUtil;
import com.kk.mumuchat.common.core.utils.core.MapUtil;
import com.kk.mumuchat.common.core.utils.core.ObjectUtil;
import com.kk.mumuchat.common.core.utils.core.StrUtil;
import com.kk.mumuchat.common.security.utils.SecurityUserUtils;
import com.kk.mumuchat.system.api.authority.constant.AuthorityConstants;
import com.kk.mumuchat.system.api.authority.domain.dto.SysMenuDto;
import com.kk.mumuchat.system.api.authority.domain.dto.SysModuleDto;
import com.kk.mumuchat.system.api.model.DataScope;
import com.kk.mumuchat.system.api.model.LoginUser;
import com.kk.mumuchat.system.authority.domain.vo.SysAuthTree;
import com.kk.mumuchat.system.authority.manager.ISysMenuManager;
import com.kk.mumuchat.system.authority.manager.ISysModuleManager;
import com.kk.mumuchat.system.authority.service.ISysAuthService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 权限管理 服务层处理
 *
 * @author mumuchat
 */
@Service
public class SysAuthServiceImpl implements ISysAuthService {

    @Resource
    private ISysMenuManager menuManager;

    @Resource
    private ISysModuleManager moduleManager;

    /**
     * 获取公共模块|菜单权限树
     *
     * @return 权限对象集合
     */
    @Override
    public List<SysAuthTree> selectCommonAuthScope() {
        List<SysModuleDto> modules = moduleManager.selectCommonList();
        List<SysMenuDto> menus = menuManager.selectCommonList();
        return buildAuthTree(modules, menus);
    }

    /**
     * 获取企业模块|菜单权限树
     *
     * @return 权限对象集合
     */
    @Override
    public List<SysAuthTree> selectEnterpriseAuthScope() {
        LoginUser loginUser = SecurityUserUtils.getLoginUser();
        DataScope dataScope = loginUser.getDataScope();
        List<SysModuleDto> modules = moduleManager.selectEnterpriseList(dataScope.getAuthGroupIds(), dataScope.getRoleIds(), loginUser.getIsLessor(), loginUser.getUserType());
        List<SysMenuDto> menus = menuManager.selectEnterpriseList(dataScope.getAuthGroupIds(), dataScope.getRoleIds(), loginUser.getIsLessor(), loginUser.getUserType());
        return buildAuthTree(modules, menus);
    }

    /**
     * 构建权限树结构
     *
     * @param modules 模块列表
     * @param menus   菜单列表
     * @return 权限树列表
     */
    private List<SysAuthTree> buildAuthTree(List<SysModuleDto> modules, List<SysMenuDto> menus) {
        // 提取所有菜单的版本信息并去重
        Set<String> menuVersions = menus.stream()
                .map(item -> StrUtil.format("{}", item.getVersion()))
                .collect(Collectors.toSet());

        // 构建模块权限树列表
        List<SysAuthTree> treeList = modules.stream().map(SysAuthTree::new).collect(Collectors.toList());

        // 用于存储模块ID与版本的组合映射关系
        Map<String, Long> menuVersionMap = new HashMap<>();

        // 为每个模块和版本组合创建版本节点
        List<SysAuthTree> versionList = menuVersions.stream()
                .map(version -> modules.stream()
                        .map(moduleItem -> {
                            // 创建版本节点
                            SysAuthTree versionAuth = new SysAuthTree();
                            versionAuth.setId(IdUtil.getSnowflakeNextId());
                            versionAuth.setLabel(StrUtil.getStrOrBlankElse(version, "默认"));
                            versionAuth.setParentId(moduleItem.getId());
                            versionAuth.setType(AuthorityConstants.AuthorityType.VERSION.getCode());
                            versionAuth.setStatus(BaseConstants.Status.NORMAL.getCode());

                            // 记录模块ID与版本组合到版本节点ID的映射关系
                            menuVersionMap.put(StrUtil.format("{}-{}", moduleItem.getId(), version), versionAuth.getId());
                            return versionAuth;
                        }).toList())
                .flatMap(Collection::stream)
                .toList();
        Map<Long, Boolean> versionContainsMap = new HashMap<>();

        // 构建菜单权限节点，并关联到对应的版本节点
        List<SysAuthTree> menuAuthList = menus.stream().map(item -> {
                    SysAuthTree menuAuth = new SysAuthTree(item);
                    if (ObjectUtil.equals(item.getParentId(), AuthorityConstants.MENU_TOP_NODE)) {
                        // 设置菜单的父节点为对应版本节点
                        Optional.ofNullable(menuVersionMap.get(StrUtil.format("{}-{}", item.getModuleId(), item.getVersion())))
                                        .ifPresent(parentId -> {
                                            menuAuth.setParentId(parentId);
                                            versionContainsMap.put(parentId, Boolean.TRUE);
                                        });
                    }
                    return menuAuth;
                })
                // 过滤掉无法找到父节点的菜单项
                .filter(item -> ObjectUtil.isNotNull(item.getParentId()))
                .toList();

        // 将菜单节点添加到权限树列表中
        treeList.addAll(menuAuthList);
        // 将版本节点添加到权限树列表中 | 仅加入存在菜单的数据
        treeList.addAll(versionList.stream().filter(item -> MapUtil.containsKey(versionContainsMap, item.getId())).toList());

        // 构建并返回完整的权限树
        return TreeUtil.buildTree(treeList);
    }
}
