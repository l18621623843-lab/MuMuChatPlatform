package com.kk.mumuchat.system.organize.domain.correlate;

import com.kk.mumuchat.common.web.correlate.domain.BaseCorrelate;
import com.kk.mumuchat.common.web.correlate.domain.Direct;
import com.kk.mumuchat.common.web.correlate.domain.Indirect;
import com.kk.mumuchat.common.web.correlate.service.CorrelateService;
import com.kk.mumuchat.system.api.organize.domain.dto.SysDeptDto;
import com.kk.mumuchat.system.api.organize.domain.dto.SysPostDto;
import com.kk.mumuchat.system.organize.domain.merge.SysOrganizeRoleMerge;
import com.kk.mumuchat.system.organize.domain.merge.SysRoleDeptMerge;
import com.kk.mumuchat.system.organize.mapper.merge.SysOrganizeRoleMergeMapper;
import com.kk.mumuchat.system.organize.mapper.merge.SysRoleDeptMergeMapper;
import com.kk.mumuchat.system.organize.service.ISysPostService;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static com.kk.mumuchat.common.web.correlate.contant.CorrelateConstants.SubOperate.DELETE;
import static com.kk.mumuchat.common.web.correlate.contant.CorrelateConstants.SubOperate.EDIT;
import static com.kk.mumuchat.common.web.correlate.contant.CorrelateConstants.SubOperate.SELECT;

/**
 * 系统服务|组织模块|部门 关联映射
 */
@Getter
@AllArgsConstructor
public enum SysDeptCorrelate implements CorrelateService {

    ROLE_SEL("角色组查询|关联（组织-角色）", new ArrayList<>() {{
        // 部门|组织-角色
        add(new Indirect<>(SELECT, SysOrganizeRoleMergeMapper.class, SysOrganizeRoleMerge::getDeptId, SysOrganizeRoleMerge::getRoleId, SysDeptDto::getId, SysDeptDto::getRoleIds));
    }}),
    ROLE_EDIT("角色组查询|关联（组织-角色）", new ArrayList<>() {{
        // 部门|组织-角色
        add(new Indirect<>(EDIT, SysOrganizeRoleMergeMapper.class, SysOrganizeRoleMerge::getDeptId, SysOrganizeRoleMerge::getRoleId, SysDeptDto::getId, SysDeptDto::getRoleIds));
    }}),
    BASE_LIST("列表|（从属岗位）", new ArrayList<>() {{
        // 部门|岗位
        add(new Direct<>(SELECT, ISysPostService.class, SysDeptDto::getId, SysPostDto::getDeptId, SysDeptDto::getSubList));
    }}),
    BASE_DEL("默认删除|（岗位|组织-角色|角色-部门）", new ArrayList<>() {{
        // 部门|岗位
        add(new Direct<>(DELETE, ISysPostService.class, SysDeptDto::getId, SysPostDto::getDeptId));
        // 部门|组织-角色
        add(new Indirect<>(DELETE, SysOrganizeRoleMergeMapper.class, SysOrganizeRoleMerge::getDeptId, SysDeptDto::getId));
        // 部门|角色-部门
        add(new Indirect<>(DELETE, SysRoleDeptMergeMapper.class, SysRoleDeptMerge::getDeptId, SysDeptDto::getId));
    }});

    private final String info;
    private final List<? extends BaseCorrelate<?>> correlates;

}