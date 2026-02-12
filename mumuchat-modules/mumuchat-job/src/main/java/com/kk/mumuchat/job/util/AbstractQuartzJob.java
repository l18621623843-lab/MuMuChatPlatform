package com.kk.mumuchat.job.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.kk.mumuchat.common.core.constant.basic.DictConstants;
import com.kk.mumuchat.common.core.constant.basic.TenantConstants;
import com.kk.mumuchat.common.core.context.SecurityContextHolder;
import com.kk.mumuchat.common.core.utils.ExceptionUtil;
import com.kk.mumuchat.common.core.utils.core.ArrayUtil;
import com.kk.mumuchat.common.core.utils.core.ObjectUtil;
import com.kk.mumuchat.common.core.utils.core.ReUtil;
import com.kk.mumuchat.common.core.utils.core.SpringUtil;
import com.kk.mumuchat.common.core.utils.core.StrUtil;
import com.kk.mumuchat.job.api.domain.dto.SysJobDto;
import com.kk.mumuchat.job.api.domain.dto.SysJobLogDto;
import com.kk.mumuchat.job.api.feign.RemoteJobLogService;
import com.kk.mumuchat.job.constant.ScheduleConstants;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * 抽象quartz调用
 *
 * @author xueyi
 */
@Slf4j
public abstract class AbstractQuartzJob implements Job {

    /** 线程本地变量 */
    private static final ThreadLocal<LocalDateTime> threadLocal = new ThreadLocal<>();

    protected RemoteJobLogService remoteJobLogService;

    protected RemoteJobLogService getRemoteJobLogService() {
        if (ObjectUtil.isNull(remoteJobLogService)) {
            remoteJobLogService = SpringUtil.getBean(RemoteJobLogService.class);
        }
        return remoteJobLogService;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        SysJobDto sysJob = new SysJobDto();
        BeanUtil.copyProperties(context.getMergedJobDataMap().get(ScheduleConstants.TASK_PROPERTIES), sysJob);
        try {
            before(context, sysJob);
            if (ObjectUtil.isNotNull(sysJob)) {
                doExecute(context, sysJob);
            }
            after(context, sysJob, null);
        } catch (Exception e) {
            log.error("任务执行异常  - ：", e);
            after(context, sysJob, e);
        }
    }

    /**
     * 执行前
     *
     * @param context 工作执行上下文对象
     * @param sysJob  系统计划任务
     */
    protected void before(JobExecutionContext context, SysJobDto sysJob) {
        threadLocal.set(LocalDateTime.now());
    }

    /**
     * 执行后
     *
     * @param context 工作执行上下文对象
     * @param job     系统计划任务
     */
    protected void after(JobExecutionContext context, SysJobDto job, Exception e) {
        LocalDateTime startTime = threadLocal.get();
        threadLocal.remove();

        final SysJobLogDto jobLog = new SysJobLogDto();
        jobLog.setJobId(job.getId());
        jobLog.setName(job.getName());
        jobLog.setJobGroup(job.getJobGroup());
        jobLog.setServerType(job.getServerType());
        jobLog.setHttpType(job.getHttpType());
        jobLog.setApiUrl(job.getApiUrl());
        jobLog.setInvokeTarget(job.getInvokeTarget());
        jobLog.setInvokeTenant(job.getInvokeTenant());
        jobLog.setStartTime(startTime);
        jobLog.setStopTime(LocalDateTime.now());
        Duration between = LocalDateTimeUtil.between(jobLog.getStartTime(), jobLog.getStopTime());
        if (StrUtil.isNotBlank(job.getResult())) {
            jobLog.setJobMessage(StrUtil.format("任务耗时：{} 总共耗时：{}毫秒；\n执行结果：{}", jobLog.getName(), between.toMillis(), job.getResult()));
        } else {
            jobLog.setJobMessage(StrUtil.format("{} 总共耗时：{}毫秒", jobLog.getName(), between.toMillis()));
        }
        if (e != null) {
            jobLog.setStatus(DictConstants.DicStatus.FAIL.getCode());
            String errorMsg = StrUtil.sub(ExceptionUtil.getExceptionMessage(e), 0, 2000);
            jobLog.setExceptionInfo(errorMsg);
        } else {
            jobLog.setStatus(DictConstants.DicStatus.NORMAL.getCode());
        }
        String[] methodParams = jobLog.getInvokeTenant().split(",(?=([^\"']*[\"'][^\"']*[\"'])*[^\"']*$)");
        Long enterpriseId = Optional.of(methodParams)
                .filter(ArrayUtil::isNotEmpty)
                .map(params -> StrUtil.trimToEmpty(params[0]))
                .map(str -> ReUtil.getGroup0("^\\d+", str))
                .filter(StrUtil::isNotBlank)
                .map(Long::valueOf)
                .orElse(null);
        // 写入数据库当中
        if (ObjectUtil.isNotNull(enterpriseId)) {
            SecurityContextHolder.setEnterpriseIdFun(enterpriseId, () -> {
                getRemoteJobLogService().addInner(jobLog);
            });
        } else {
            SecurityContextHolder.setSourceNameFun(TenantConstants.Source.SLAVE.getCode(), () -> {
                getRemoteJobLogService().addInner(jobLog);
            });
        }
    }

    /**
     * 执行方法，由子类重载
     *
     * @param context 工作执行上下文对象
     * @param sysJob  系统计划任务
     * @throws Exception 执行过程中的异常
     */
    protected abstract void doExecute(JobExecutionContext context, SysJobDto sysJob) throws Exception;
}