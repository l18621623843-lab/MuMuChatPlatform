package com.kk.mumuchat.tenant.source.service;

import com.kk.mumuchat.common.web.entity.service.IBaseService;
import com.kk.mumuchat.tenant.api.source.domain.dto.TeSourceDto;
import com.kk.mumuchat.tenant.api.source.domain.query.TeSourceQuery;

/**
 * 租户服务 | 策略模块 | 数据源管理 服务层
 *
 * @author mumuchat
 */
public interface ITeSourceService extends IBaseService<TeSourceQuery, TeSourceDto> {

    /**
     * 校验数据源是否为默认数据源
     *
     * @param id 数据源id
     * @return 结果 | true/false 是/不是
     */
    boolean checkIsDefault(Long id);
}