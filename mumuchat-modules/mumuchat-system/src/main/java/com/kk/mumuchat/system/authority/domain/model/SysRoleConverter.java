package com.kk.mumuchat.system.authority.domain.model;

import com.kk.mumuchat.common.core.web.entity.model.BaseConverter;
import com.kk.mumuchat.system.api.authority.domain.dto.SysRoleDto;
import com.kk.mumuchat.system.api.authority.domain.po.SysRolePo;
import com.kk.mumuchat.system.api.authority.domain.query.SysRoleQuery;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * 系统服务|权限模块|角色 对象映射器
 *
 * @author xueyi
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SysRoleConverter extends BaseConverter<SysRoleQuery, SysRoleDto, SysRolePo> {
}
