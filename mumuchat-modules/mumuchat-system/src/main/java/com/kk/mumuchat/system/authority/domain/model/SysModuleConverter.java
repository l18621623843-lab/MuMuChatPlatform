package com.kk.mumuchat.system.authority.domain.model;

import com.kk.mumuchat.common.core.web.entity.model.BaseConverter;
import com.kk.mumuchat.system.api.authority.domain.dto.SysModuleDto;
import com.kk.mumuchat.system.api.authority.domain.po.SysModulePo;
import com.kk.mumuchat.system.api.authority.domain.query.SysModuleQuery;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * 系统服务|权限模块|模块 对象映射器
 *
 * @author mumuchat
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SysModuleConverter extends BaseConverter<SysModuleQuery, SysModuleDto, SysModulePo> {
}
