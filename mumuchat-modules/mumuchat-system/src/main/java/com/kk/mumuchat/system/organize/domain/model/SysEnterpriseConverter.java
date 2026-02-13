package com.kk.mumuchat.system.organize.domain.model;

import com.kk.mumuchat.common.core.web.entity.model.BaseConverter;
import com.kk.mumuchat.system.api.organize.domain.dto.SysEnterpriseDto;
import com.kk.mumuchat.system.api.organize.domain.po.SysEnterprisePo;
import com.kk.mumuchat.system.api.organize.domain.query.SysEnterpriseQuery;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * 系统服务|组织模块|企业 对象映射器
 *
 * @author mumuchat
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SysEnterpriseConverter extends BaseConverter<SysEnterpriseQuery, SysEnterpriseDto, SysEnterprisePo> {
}
