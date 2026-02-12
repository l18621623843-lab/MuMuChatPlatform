package com.kk.mumuchat.tenant.source.domain.model;

import com.kk.mumuchat.common.core.web.entity.model.BaseConverter;
import com.kk.mumuchat.tenant.api.source.domain.dto.TeSourceDto;
import com.kk.mumuchat.tenant.api.source.domain.po.TeSourcePo;
import com.kk.mumuchat.tenant.api.source.domain.query.TeSourceQuery;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * 租户服务 | 策略模块 | 数据源 对象映射器
 *
 * @author xueyi
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TeSourceConverter extends BaseConverter<TeSourceQuery, TeSourceDto, TeSourcePo> {
}