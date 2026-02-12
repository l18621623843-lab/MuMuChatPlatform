package com.kk.mumuchat.tenant.source.manager;

import com.kk.mumuchat.common.web.entity.manager.IBaseManager;
import com.kk.mumuchat.tenant.api.source.domain.dto.TeSourceDto;
import com.kk.mumuchat.tenant.api.source.domain.query.TeSourceQuery;

/**
 * 租户服务 | 策略模块 | 数据源管理 数据封装层
 *
 * @author xueyi
 */
public interface ITeSourceManager extends IBaseManager<TeSourceQuery, TeSourceDto> {
}