package com.kk.mumuchat.system.monitor.service;

import com.kk.mumuchat.common.web.entity.service.IBaseService;
import com.kk.mumuchat.system.api.log.domain.dto.SysOperateLogDto;
import com.kk.mumuchat.system.api.log.domain.query.SysOperateLogQuery;

/**
 * 系统服务|监控模块|操作日志管理 服务层
 *
 * @author xueyi
 */
public interface ISysOperateLogService extends IBaseService<SysOperateLogQuery, SysOperateLogDto> {

    /**
     * 清空操作日志
     */
    void cleanOperateLog();
}
