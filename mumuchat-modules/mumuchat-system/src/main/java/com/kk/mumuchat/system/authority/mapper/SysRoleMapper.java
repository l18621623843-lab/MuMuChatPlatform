package com.kk.mumuchat.system.authority.mapper;

import com.kk.mumuchat.common.datasource.annotation.Isolate;
import com.kk.mumuchat.common.web.entity.mapper.BaseMapper;
import com.kk.mumuchat.system.api.authority.domain.dto.SysRoleDto;
import com.kk.mumuchat.system.api.authority.domain.po.SysRolePo;
import com.kk.mumuchat.system.api.authority.domain.query.SysRoleQuery;

/**
 * 岗位管理 数据层
 *
 * @author xueyi
 */
@Isolate
public interface SysRoleMapper extends BaseMapper<SysRoleQuery, SysRoleDto, SysRolePo> {
}
