package com.kk.mumuchat.tenant.source.mapper;

import com.kk.mumuchat.common.datasource.annotation.Master;
import com.kk.mumuchat.common.web.entity.mapper.BaseMapper;
import com.kk.mumuchat.tenant.api.source.domain.dto.TeSourceDto;
import com.kk.mumuchat.tenant.api.source.domain.po.TeSourcePo;
import com.kk.mumuchat.tenant.api.source.domain.query.TeSourceQuery;

/**
 * 租户服务 | 策略模块 | 数据源管理 数据层
 *
 * @author xueyi
 */
@Master
public interface TeSourceMapper extends BaseMapper<TeSourceQuery, TeSourceDto, TeSourcePo> {
}