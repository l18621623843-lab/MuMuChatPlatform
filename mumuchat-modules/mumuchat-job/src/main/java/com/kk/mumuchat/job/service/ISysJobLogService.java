package com.kk.mumuchat.job.service;

import com.kk.mumuchat.common.web.entity.service.IBaseService;
import com.kk.mumuchat.job.api.domain.dto.SysJobLogDto;
import com.kk.mumuchat.job.api.domain.query.SysJobLogQuery;

/**
 * 调度日志管理 服务层
 *
 * @author mumuchat
 */
public interface ISysJobLogService extends IBaseService<SysJobLogQuery, SysJobLogDto> {

    /**
     * 清空任务日志
     */
    public void cleanLog();
}
