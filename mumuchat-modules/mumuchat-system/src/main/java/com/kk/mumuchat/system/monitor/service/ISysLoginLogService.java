package com.kk.mumuchat.system.monitor.service;

import com.kk.mumuchat.common.web.entity.service.IBaseService;
import com.kk.mumuchat.system.api.log.domain.dto.SysLoginLogDto;
import com.kk.mumuchat.system.api.log.domain.query.SysLoginLogQuery;

/**
 * 系统服务|监控模块|访问日志管理 服务层
 *
 * @author mumuchat
 */
public interface ISysLoginLogService extends IBaseService<SysLoginLogQuery, SysLoginLogDto> {

    /**
     * 清空系统登录日志
     */
    void cleanLoginLog();
}
