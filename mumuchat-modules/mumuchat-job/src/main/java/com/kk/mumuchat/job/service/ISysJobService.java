package com.kk.mumuchat.job.service;

import com.kk.mumuchat.common.core.properties.RemoteServiceProperties;
import com.kk.mumuchat.common.web.entity.service.IBaseService;
import com.kk.mumuchat.job.api.domain.dto.SysJobDto;
import com.kk.mumuchat.job.api.domain.query.SysJobQuery;
import org.quartz.SchedulerException;

import java.util.List;

/**
 * 调度任务管理 服务层
 *
 * @author mumuchat
 */
public interface ISysJobService extends IBaseService<SysJobQuery, SysJobDto> {

    /**
     * 查询调度服务映射列表
     *
     * @return 服务映射配置列表
     */
    List<RemoteServiceProperties.RemoteService> selectInnerServiceMapping();

    /**
     * 暂停任务
     *
     * @param job 调度信息
     * @return 结果
     */
    int pauseJob(SysJobDto job) throws SchedulerException;

    /**
     * 恢复任务
     *
     * @param job 调度信息
     * @return 结果
     */
    int resumeJob(SysJobDto job) throws SchedulerException;

    /**
     * 立即运行任务
     *
     * @param id Id
     */
    boolean run(Long id) throws SchedulerException;

}