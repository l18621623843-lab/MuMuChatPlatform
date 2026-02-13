package com.kk.mumuchat.tenant.source.mapper;

import com.kk.mumuchat.common.datasource.annotation.Master;
import com.kk.mumuchat.common.web.entity.mapper.BaseMapper;
import com.kk.mumuchat.tenant.api.source.domain.dto.TeStrategyDto;
import com.kk.mumuchat.tenant.api.source.domain.po.TeStrategyPo;
import com.kk.mumuchat.tenant.api.source.domain.query.TeStrategyQuery;

/**
 * 租户服务 | 策略模块 | 源策略管理 数据层
 *
 * @author mumuchat
 */
@Master
public interface TeStrategyMapper extends BaseMapper<TeStrategyQuery, TeStrategyDto, TeStrategyPo> {
}