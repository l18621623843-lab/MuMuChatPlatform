package com.kk.mumuchat.system.authority.domain.model;

import com.kk.mumuchat.common.core.web.entity.model.BaseConverter;
import com.kk.mumuchat.system.api.authority.domain.dto.SysClientDto;
import com.kk.mumuchat.system.api.authority.domain.po.SysClientPo;
import com.kk.mumuchat.system.api.authority.domain.query.SysClientQuery;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * 系统服务|权限模块|客户端 对象映射器
 *
 * @author xueyi
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SysClientConverter extends BaseConverter<SysClientQuery, SysClientDto, SysClientPo> {
}