package com.kk.mumuchat.gen.domain.model;

import com.kk.mumuchat.common.core.web.entity.model.BaseConverter;
import com.kk.mumuchat.gen.domain.dto.GenTableDto;
import com.kk.mumuchat.gen.domain.po.GenTablePo;
import com.kk.mumuchat.gen.domain.query.GenTableQuery;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * 业务 对象映射器
 *
 * @author xueyi
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface GenTableConverter extends BaseConverter<GenTableQuery, GenTableDto, GenTablePo> {
}