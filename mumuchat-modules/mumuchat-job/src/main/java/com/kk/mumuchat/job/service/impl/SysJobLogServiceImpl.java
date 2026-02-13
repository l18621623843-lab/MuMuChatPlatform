package com.kk.mumuchat.job.service.impl;

import com.kk.mumuchat.common.web.entity.service.impl.BaseServiceImpl;
import com.kk.mumuchat.job.api.domain.dto.SysJobLogDto;
import com.kk.mumuchat.job.api.domain.query.SysJobLogQuery;
import com.kk.mumuchat.job.domain.correlate.SysJobLogCorrelate;
import com.kk.mumuchat.job.manager.ISysJobLogManager;
import com.kk.mumuchat.job.service.ISysJobLogService;
import org.springframework.stereotype.Service;

/**
 * 调度日志管理 服务层处理
 *
 * @author mumuchat
 */
@Service
public class SysJobLogServiceImpl extends BaseServiceImpl<SysJobLogQuery, SysJobLogDto, SysJobLogCorrelate, ISysJobLogManager> implements ISysJobLogService {

    /**
     * 清空任务日志
     */
    @Override
    public void cleanLog() {
        baseManager.cleanLog();
    }
}
