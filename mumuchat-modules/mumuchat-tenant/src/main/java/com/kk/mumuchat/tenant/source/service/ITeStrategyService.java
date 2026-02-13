package com.kk.mumuchat.tenant.source.service;

import com.kk.mumuchat.common.web.entity.service.IBaseService;
import com.kk.mumuchat.tenant.api.source.domain.dto.TeStrategyDto;
import com.kk.mumuchat.tenant.api.source.domain.query.TeStrategyQuery;

/**
 * 租户服务 | 策略模块 | 源策略管理 服务层
 *
 * @author mumuchat
 */
public interface ITeStrategyService extends IBaseService<TeStrategyQuery, TeStrategyDto> {

    /**
     * 校验数据源是否被使用
     *
     * @param sourceId 数据源id
     * @return 结果 | true/false 存在/不存在
     */
    boolean checkSourceExist(Long sourceId);

    /**
     * 校验源策略是否为默认源策略
     *
     * @param id 源策略id
     * @return 结果 | true/false 是/不是
     */
    boolean checkIsDefault(Long id);
}