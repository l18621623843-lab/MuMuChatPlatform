package com.kk.mumuchat.tenant.source.domain.model;

import com.kk.mumuchat.common.core.web.entity.model.BaseConverter;
import com.kk.mumuchat.tenant.api.source.domain.dto.TeStrategyDto;
import com.kk.mumuchat.tenant.api.source.domain.po.TeStrategyPo;
import com.kk.mumuchat.tenant.api.source.domain.query.TeStrategyQuery;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * 租户服务 | 策略模块 | 源策略 对象映射器
 *
 * @author xueyi
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TeStrategyConverter extends BaseConverter<TeStrategyQuery, TeStrategyDto, TeStrategyPo> {
}