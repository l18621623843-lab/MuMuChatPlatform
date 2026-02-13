package com.kk.mumuchat.job.manager;

import com.kk.mumuchat.common.web.entity.manager.IBaseManager;
import com.kk.mumuchat.job.api.domain.dto.SysJobDto;
import com.kk.mumuchat.job.api.domain.query.SysJobQuery;

import java.util.List;

/**
 * 调度任务管理 数据封装层
 *
 * @author mumuchat
 */
public interface ISysJobManager extends IBaseManager<SysJobQuery, SysJobDto> {

    /**
     * 项目启动时
     */
    List<SysJobDto> initScheduler();
}
