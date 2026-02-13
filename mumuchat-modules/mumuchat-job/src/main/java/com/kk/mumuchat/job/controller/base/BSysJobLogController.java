package com.kk.mumuchat.job.controller.base;

import com.kk.mumuchat.common.web.entity.controller.BaseController;
import com.kk.mumuchat.job.api.domain.dto.SysJobLogDto;
import com.kk.mumuchat.job.api.domain.query.SysJobLogQuery;
import com.kk.mumuchat.job.service.ISysJobLogService;

/**
 * 定时任务|调度日志管理 | 通用 业务处理
 *
 * @author mumuchat
 */
public class BSysJobLogController extends BaseController<SysJobLogQuery, SysJobLogDto, ISysJobLogService> {

    /** 定义节点名称 */
    @Override
    protected String getNodeName() {
        return "调度日志";
    }
}
