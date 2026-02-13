package com.kk.mumuchat.tenant.tenant.domain.model;

import com.kk.mumuchat.common.core.web.entity.model.BaseConverter;
import com.kk.mumuchat.common.core.web.model.SysEnterprise;
import com.kk.mumuchat.tenant.api.tenant.domain.dto.TeTenantDto;
import com.kk.mumuchat.tenant.api.tenant.domain.po.TeTenantPo;
import com.kk.mumuchat.tenant.api.tenant.domain.query.TeTenantQuery;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * 租户服务 | 租户模块 | 租户 对象映射器
 *
 * @author mumuchat
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TeTenantConverter extends BaseConverter<TeTenantQuery, TeTenantDto, TeTenantPo> {

    SysEnterprise mapper(TeTenantDto dto);
}