package com.kk.mumuchat.job.api.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.kk.mumuchat.common.core.web.tenant.base.TBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 调度日志 持久化对象
 *
 * @author xueyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_job_log", excludeProperty = {"createBy", "updateBy", "remark", "updateTime", "sort"})
public class SysJobLogPo extends TBaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 任务Id */
    protected Long jobId;

    /** 任务组名 */
    protected String jobGroup;

    /** 归属服务 */
    protected String serverType;

    /** 请求类型 */
    protected String httpType;

    /** 请求地址 */
    protected String apiUrl;

    /** 调用目标字符串 */
    protected String invokeTarget;

    /** 调用租户字符串 */
    protected String invokeTenant;

    /** 日志信息 */
    protected String jobMessage;

    /** 执行状态（0正常 1失败） */
    protected String status;

    /** 异常信息 */
    protected String exceptionInfo;

}
