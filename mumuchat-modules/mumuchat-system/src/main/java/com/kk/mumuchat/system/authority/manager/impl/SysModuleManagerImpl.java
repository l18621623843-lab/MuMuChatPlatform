package com.kk.mumuchat.system.authority.manager.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.kk.mumuchat.common.core.constant.basic.BaseConstants;
import com.kk.mumuchat.common.core.constant.basic.DictConstants;
import com.kk.mumuchat.common.core.constant.basic.SecurityConstants;
import com.kk.mumuchat.common.core.utils.core.CollUtil;
import com.kk.mumuchat.common.web.entity.manager.impl.BaseManagerImpl;
import com.kk.mumuchat.system.api.authority.domain.dto.SysModuleDto;
import com.kk.mumuchat.system.api.authority.domain.po.SysModulePo;
import com.kk.mumuchat.system.api.authority.domain.query.SysModuleQuery;
import com.kk.mumuchat.system.api.organize.domain.dto.SysUserDto;
import com.kk.mumuchat.system.authority.domain.merge.SysAuthGroupModuleMerge;
import com.kk.mumuchat.system.authority.domain.merge.SysRoleModuleMerge;
import com.kk.mumuchat.system.authority.domain.model.SysModuleConverter;
import com.kk.mumuchat.system.authority.manager.ISysModuleManager;
import com.kk.mumuchat.system.authority.mapper.SysModuleMapper;
import com.kk.mumuchat.system.authority.mapper.merge.SysAuthGroupModuleMergeMapper;
import com.kk.mumuchat.system.authority.mapper.merge.SysRoleModuleMergeMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 系统服务|权限模块|模块管理 数据封装层处理
 *
 * @author mumuchat
 */
@Component
public class SysModuleManagerImpl extends BaseManagerImpl<SysModuleQuery, SysModuleDto, SysModulePo, SysModuleMapper, SysModuleConverter> implements ISysModuleManager {

    @Resource
    private SysRoleModuleMergeMapper roleModuleMergeMapper;

    @Resource
    private SysAuthGroupModuleMergeMapper authGroupModuleMergeMapper;

    /**
     * 获取全部状态正常公共模块
     *
     * @return 模块对象集合
     */
    @Override
    public List<SysModuleDto> selectCommonList() {
        List<SysModulePo> moduleList = baseMapper.selectList(Wrappers.<SysModulePo>lambdaQuery()
                .eq(SysModulePo::getIsCommon, DictConstants.DicCommonPrivate.COMMON.getCode())
                .eq(SysModulePo::getStatus, BaseConstants.Status.NORMAL.getCode()));
        return mapperDto(moduleList);
    }

    /**
     * 获取企业有权限且状态正常的模块
     *
     * @param authGroupIds 企业权限组Id集合
     * @param roleIds      角色Id集合
     * @param isLessor     租户标识
     * @param userType     用户标识
     * @return 模块对象集合
     */
    @Override
    public List<SysModuleDto> selectEnterpriseList(Set<Long> authGroupIds, Set<Long> roleIds, String isLessor, String userType) {
        List<SysModulePo> moduleList;
        // 租管租户 && 超级管理员
        if (SecurityConstants.TenantType.isLessor(isLessor) && SysUserDto.isAdmin(userType)) {
            moduleList = baseMapper.selectList(Wrappers.<SysModulePo>lambdaQuery()
                    .eq(SysModulePo::getStatus, BaseConstants.Status.NORMAL.getCode()));
        } else {
            Set<Long> authGroupModuleIds;
            if (SecurityConstants.TenantType.isNotLessor(isLessor)) {
                if (CollUtil.isEmpty(authGroupIds)) {
                    return new ArrayList<>();
                }
                // 获取租户权限组范围内的最下级菜单Ids
                List<SysAuthGroupModuleMerge> authGroupMenuMerges = authGroupModuleMergeMapper.selectList(Wrappers.<SysAuthGroupModuleMerge>lambdaQuery()
                        .in(SysAuthGroupModuleMerge::getAuthGroupId, authGroupIds));
                if (CollUtil.isEmpty(authGroupMenuMerges)) {
                    return new ArrayList<>();
                }
                authGroupModuleIds = authGroupMenuMerges.stream().map(SysAuthGroupModuleMerge::getModuleId).collect(Collectors.toSet());
            } else {
                authGroupModuleIds = new HashSet<>();
            }
            // 普通租户 && 超级管理员
            if (SysUserDto.isAdmin(userType)) {
                moduleList = baseMapper.selectList(Wrappers.<SysModulePo>lambdaQuery()
                        .eq(SysModulePo::getStatus, BaseConstants.Status.NORMAL.getCode())
                        .eq(CollUtil.isEmpty(authGroupModuleIds), SysModulePo::getIsCommon, DictConstants.DicCommonPrivate.PRIVATE.getCode())
                        .and(CollUtil.isNotEmpty(authGroupModuleIds), a -> a.eq(SysModulePo::getIsCommon, DictConstants.DicCommonPrivate.PRIVATE.getCode())
                                .or().in(SysModulePo::getId, authGroupModuleIds)));
            } else {
                if (CollUtil.isEmpty(roleIds)) {
                    return new ArrayList<>();
                }
                List<SysRoleModuleMerge> roleModuleMerges = roleModuleMergeMapper.selectList(Wrappers.<SysRoleModuleMerge>lambdaQuery()
                        .in(SysRoleModuleMerge::getRoleId, roleIds));
                if (CollUtil.isEmpty(roleModuleMerges)) {
                    return new ArrayList<>();
                }
                Set<Long> roleModuleIds = roleModuleMerges.stream().map(SysRoleModuleMerge::getModuleId).collect(Collectors.toSet());
                // 租管租户 && 普通用户
                if (SecurityConstants.TenantType.isLessor(isLessor)) {
                    moduleList = baseMapper.selectList(Wrappers.<SysModulePo>lambdaQuery()
                            .eq(SysModulePo::getStatus, BaseConstants.Status.NORMAL.getCode())
                            .in(SysModulePo::getId, roleModuleIds));
                }
                // 普通租户 && 普通用户
                else {
                    moduleList = baseMapper.selectList(Wrappers.<SysModulePo>lambdaQuery()
                            .eq(SysModulePo::getStatus, BaseConstants.Status.NORMAL.getCode())
                            .in(SysModulePo::getId, roleModuleIds)
                            .eq(CollUtil.isEmpty(authGroupModuleIds), SysModulePo::getIsCommon, DictConstants.DicCommonPrivate.PRIVATE.getCode())
                            .and(CollUtil.isNotEmpty(authGroupModuleIds), a -> a.eq(SysModulePo::getIsCommon, DictConstants.DicCommonPrivate.PRIVATE.getCode())
                                    .or().in(SysModulePo::getId, authGroupModuleIds)));
                }
            }
        }
        return mapperDto(moduleList);
    }
}
