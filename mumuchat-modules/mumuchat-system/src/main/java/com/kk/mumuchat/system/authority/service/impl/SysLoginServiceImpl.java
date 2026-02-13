package com.kk.mumuchat.system.authority.service.impl;

import com.kk.mumuchat.common.core.constant.basic.BaseConstants;
import com.kk.mumuchat.common.core.constant.basic.SecurityConstants;
import com.kk.mumuchat.common.core.utils.core.CollUtil;
import com.kk.mumuchat.common.core.utils.core.StrUtil;
import com.kk.mumuchat.common.core.utils.ip.IpUtil;
import com.kk.mumuchat.system.api.authority.domain.dto.SysMenuDto;
import com.kk.mumuchat.system.api.authority.domain.dto.SysModuleDto;
import com.kk.mumuchat.system.api.authority.domain.dto.SysRoleDto;
import com.kk.mumuchat.system.api.model.DataScope;
import com.kk.mumuchat.system.api.organize.domain.dto.SysDeptDto;
import com.kk.mumuchat.system.api.organize.domain.dto.SysEnterpriseDto;
import com.kk.mumuchat.system.api.organize.domain.dto.SysPostDto;
import com.kk.mumuchat.system.api.organize.domain.dto.SysUserDto;
import com.kk.mumuchat.system.api.organize.domain.query.SysEnterpriseQuery;
import com.kk.mumuchat.system.authority.service.ISysLoginService;
import com.kk.mumuchat.system.authority.service.ISysMenuService;
import com.kk.mumuchat.system.authority.service.ISysModuleService;
import com.kk.mumuchat.system.organize.service.ISysDeptService;
import com.kk.mumuchat.system.organize.service.ISysEnterpriseService;
import com.kk.mumuchat.system.organize.service.ISysOrganizeService;
import com.kk.mumuchat.system.organize.service.ISysPostService;
import com.kk.mumuchat.system.organize.service.ISysUserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.kk.mumuchat.common.core.constant.basic.SecurityConstants.PERMISSION_ADMIN;
import static com.kk.mumuchat.common.core.constant.basic.SecurityConstants.ROLE_ADMIN;
import static com.kk.mumuchat.common.core.constant.basic.SecurityConstants.ROLE_ADMINISTRATOR;

/**
 * 登录管理 服务层处理
 *
 * @author mumuchat
 */
@Service
public class SysLoginServiceImpl implements ISysLoginService {

    @Resource
    private ISysEnterpriseService enterpriseService;

    @Resource
    private ISysDeptService deptService;

    @Resource
    private ISysPostService postService;

    @Resource
    private ISysUserService userService;

    @Resource
    private ISysModuleService moduleService;

    @Resource
    private ISysMenuService menuService;

    @Resource
    private ISysOrganizeService organizeService;

    /**
     * 登录校验|根据企业账号查询企业信息
     *
     * @param enterpriseName 企业账号
     * @return 企业对象
     */
    @Override
    public SysEnterpriseDto loginByEnterpriseName(String enterpriseName) {
        return enterpriseService.selectByName(enterpriseName);
    }

    /**
     * 登录校验|登录超级管理员用户
     *
     * @return 用户对象
     */
    @Override
    public SysUserDto loginByAdminUser() {
        return userService.loginByAdminUser();
    }

    /**
     * 登录校验|根据用户账号查询用户信息
     *
     * @param userName 用户账号
     * @param password 密码
     * @return 用户对象
     */
    @Override
    public SysUserDto loginByUser(String userName, String password) {
        return userService.userLogin(userName, password);
    }

    /**
     * 登录校验|获取角色数据权限
     *
     * @param roleList 角色信息集合
     * @param isLessor 租户标识
     * @param userType 用户标识
     * @return 角色权限信息
     */
    @Override
    public Set<String> getRolePermission(List<SysRoleDto> roleList, String isLessor, String userType) {
        Set<String> roles = new HashSet<>();
        // 租管租户拥有租管标识权限
        if (SecurityConstants.TenantType.isLessor(isLessor)) {
            roles.add(ROLE_ADMINISTRATOR);
        }
        // 超管用户拥有超管标识权限
        if (SysUserDto.isAdmin(userType)) {
            roles.add(ROLE_ADMIN);
        } else {
            roles.addAll(roleList.stream().filter(item -> BaseConstants.Status.isNormal(item.getStatus())).map(SysRoleDto::getRoleKey).filter(StrUtil::isNotBlank).collect(Collectors.toSet()));
        }
        return roles;
    }

    /**
     * 登录校验|获取权限模块列表
     *
     * @param authGroupIds 企业权限组Id集合
     * @param roleIds      角色Id集合
     * @param isLessor     租户标识
     * @param userType     用户标识
     * @return 模块信息对象集合
     */
    @Override
    public List<SysModuleDto> getModuleList(Set<Long> authGroupIds, Set<Long> roleIds, String isLessor, String userType) {
        return moduleService.selectEnterpriseList(authGroupIds, roleIds, isLessor, userType);
    }

    /**
     * 登录校验|获取权限菜单列表
     *
     * @param authGroupIds 企业权限组Id集合
     * @param roleIds      角色Id集合
     * @param isLessor     租户标识
     * @param userType     用户标识
     * @return 菜单信息对象集合
     */
    @Override
    public List<SysMenuDto> getMenuList(Set<Long> authGroupIds, Set<Long> roleIds, String isLessor, String userType) {
        return menuService.selectEnterpriseList(authGroupIds, roleIds, isLessor, userType);
    }

    /**
     * 登录校验|获取菜单数据权限
     *
     * @param menuList 菜单信息对象集合
     * @param isLessor 租户标识
     * @param userType 用户标识
     * @return 菜单权限信息集合
     */
    @Override
    public Set<String> getMenuPermission(List<SysMenuDto> menuList, String isLessor, String userType) {
        Set<String> perms = new HashSet<>();
        // 租管租户的超管用户拥有所有权限
        if (SecurityConstants.TenantType.isLessor(isLessor) && SysUserDto.isAdmin(userType)) {
            perms.add(PERMISSION_ADMIN);
        } else {
            // 菜单组合权限表示集合
            Set<String> menuPerms = menuList.stream().filter(item -> BaseConstants.Status.isNormal(item.getStatus()))
                    .map(SysMenuDto::getPerms).filter(StrUtil::isNotBlank)
                    .flatMap(item -> StrUtil.split(item, StrUtil.COMMA).stream()).map(StrUtil::cleanBlank).collect(Collectors.toSet());
            perms.addAll(menuPerms);
        }
        return perms;
    }

    /**
     * 登录校验|获取数据数据权限
     *
     * @param roleList 角色信息集合
     * @param user     用户对象
     * @return 数据权限对象
     */
    @Override
    public DataScope getDataScope(List<SysRoleDto> roleList, SysUserDto user) {
        DataScope scope = new DataScope();
        // 1.判断是否为超管用户
        if (user.isAdmin()) {
            scope.setDataScope(SecurityConstants.DataScope.ALL.getCode());
            return scope;
        }
        // 2.判断有无全部数据权限角色
        for (SysRoleDto role : roleList) {
            if (StrUtil.equals(role.getDataScope(), SecurityConstants.DataScope.ALL.getCode())) {
                scope.setDataScope(SecurityConstants.DataScope.ALL.getCode());
                return scope;
            }
        }
        // 3.组建权限集
        Set<Long> deptScope = new HashSet<>(), postScope = new HashSet<>(), userScope = new HashSet<>(), customRoleId = new HashSet<>();
        int isCustom = 0, isDept = 0, isDeptAndChild = 0, isPost = 0, isSelf = 0;
        for (SysRoleDto role : roleList) {
            switch (SecurityConstants.DataScope.getByCode(role.getDataScope())) {
                case CUSTOM -> {
                    isCustom++;
                    customRoleId.add(role.getId());
                }
                case DEPT -> {
                    if (isDept++ == 0)
                        deptScope.addAll(user.getPosts().stream().map(post -> post.getDept().getId()).collect(Collectors.toSet()));
                }
                case DEPT_AND_CHILD -> {
                    if (isDeptAndChild++ == 0) {
                        Set<Long> deptIds = user.getPosts().stream().map(post -> post.getDept().getId()).collect(Collectors.toSet());
                        if (CollUtil.isNotEmpty(deptIds)) {
                            List<SysDeptDto> deptList = deptService.selectChildListByIds(deptIds);
                            deptScope.addAll(deptList.stream().map(SysDeptDto::getId).collect(Collectors.toSet()));
                        }
                    }
                }
                case POST -> {
                    if (isPost++ == 0)
                        postScope.addAll(user.getPosts().stream().map(SysPostDto::getId).collect(Collectors.toSet()));
                }
                case SELF -> {
                    if (isSelf++ == 0)
                        userScope.add(user.getId());
                }
                default -> {
                }
            }
        }

        if (isCustom > 0) {
            deptScope.addAll(organizeService.selectRoleDeptSetByRoleIds(customRoleId));
            postScope.addAll(organizeService.selectRolePostSetByRoleIds(customRoleId));
        }
        scope.setDeptScope(deptScope);
        List<SysPostDto> postList = postService.selectListByDeptIds(deptScope);
        postScope.addAll(postList.stream().map(SysPostDto::getId).collect(Collectors.toSet()));
        scope.setPostScope(postScope);
        userScope.addAll(organizeService.selectUserSetByPostIds(postScope));
        scope.setUserScope(userScope);
        if (isCustom > 0) {
            scope.setDataScope(SecurityConstants.DataScope.CUSTOM.getCode());
            return scope;
        } else if (isDeptAndChild > 0) {
            scope.setDataScope(SecurityConstants.DataScope.DEPT_AND_CHILD.getCode());
            return scope;
        } else if (isDept > 0) {
            scope.setDataScope(SecurityConstants.DataScope.DEPT.getCode());
            return scope;
        } else if (isPost > 0) {
            scope.setDataScope(SecurityConstants.DataScope.POST.getCode());
            return scope;
        } else if (isSelf > 0) {
            scope.setDataScope(SecurityConstants.DataScope.SELF.getCode());
            return scope;
        }
        scope.setDataScope(SecurityConstants.DataScope.NONE.getCode());
        return scope;
    }

    /**
     * 通过域名获取租户信息
     *
     * @param domainName 访问域名
     * @return 企业信息对象
     */
    @Override
    public SysEnterpriseDto getDomainTenant(String domainName) {
        if (StrUtil.isNotBlank(domainName)) {
            String doMainQuery;
            if ("127.0.0.1".equals(domainName) || "localhost".equals(domainName)) {
                return null;
            }
            // 校验是否为IP地址
            if (IpUtil.isIP(domainName)) {
                doMainQuery = domainName;
            } else {
                try {
                    // 校验是否为有效域名
                    InetAddress.getByName(domainName);
                    // 如果是系统默认域名且二级域名不是www则直接返回二级域名否则直接返回传入域名
                    if (StrUtil.startWith(domainName, "www.", Boolean.FALSE)) {
                        doMainQuery = StrUtil.removePrefix(domainName, "www.");
                    } else {
                        doMainQuery = domainName;
                    }
                } catch (Exception e) {
                    return null;
                }
            }
            SysEnterpriseQuery enterpriseQuery = new SysEnterpriseQuery();
            enterpriseQuery.setDomainName(doMainQuery);
            List<SysEnterpriseDto> list = enterpriseService.selectList(enterpriseQuery);
            if (CollUtil.isNotEmpty(list)) {
                return list.get(0);
            }
        }
        return null;
    }
}
