package com.kk.mumuchat.system.dict.domain.model;

import com.kk.mumuchat.common.core.web.entity.model.BaseConverter;
import com.kk.mumuchat.system.api.dict.domain.dto.SysImExDto;
import com.kk.mumuchat.system.api.dict.domain.po.SysImExPo;
import com.kk.mumuchat.system.api.dict.domain.query.SysImExQuery;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * 导入导出配置 对象映射器
 *
 * @author mumuchat
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SysImExConverter extends BaseConverter<SysImExQuery, SysImExDto, SysImExPo> {
}
