package com.kk.mumuchat.system.monitor.manager;

import com.kk.mumuchat.common.web.entity.manager.IBaseManager;
import com.kk.mumuchat.system.api.log.domain.dto.SysOperateLogDto;
import com.kk.mumuchat.system.api.log.domain.query.SysOperateLogQuery;

/**
 * 系统服务|监控模块|操作日志管理 数据封装层
 *
 * @author mumuchat
 */
public interface ISysOperateLogManager extends IBaseManager<SysOperateLogQuery, SysOperateLogDto> {

    /**
     * 清空系统操作日志
     */
    void cleanOperateLog();
}
