package com.kk.mumuchat.job.api.domain.po;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.kk.mumuchat.common.core.utils.core.StrUtil;
import com.kk.mumuchat.common.core.web.tenant.base.TBaseEntity;
import com.kk.mumuchat.job.api.utils.CronUtils;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.util.Date;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE_RIGHT;

/**
 * 调度任务 持久化对象
 *
 * @author mumuchat
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_job", excludeProperty = {"sort"})
public class SysJobPo extends TBaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 任务名称 */
    @NotBlank(message = "任务名称不能为空")
    @Size(max = 64, message = "任务名称不能超过64个字符")
    @TableField(condition = LIKE_RIGHT)
    protected String name;

    /** 任务组名 */
    @NotBlank(message = "任务类型不能为空")
    @TableField(updateStrategy = FieldStrategy.NEVER)
    protected String jobGroup;

    /** 归属服务 */
    protected String serverType;

    /** 请求类型 */
    protected String httpType;

    /** 请求地址 */
    @Size(max = 500, message = "请求地址长度不能超过500个字符")
    protected String apiUrl;

    /** 调用目标字符串 */
    @Size(max = 500, message = "调用目标字符串长度不能超过500个字符")
    protected String invokeTarget;

    /** 调用租户字符串 */
    @TableField(updateStrategy = FieldStrategy.NEVER)
    protected String invokeTenant;

    /** cron执行表达式 */
    @NotBlank(message = "Cron执行表达式不能为空")
    @Size(max = 255, message = "Cron执行表达式不能超过255个字符")
    protected String cronExpression;

    /** 计划执行错误策略（0默认 1立即执行 2执行一次 3放弃执行） */
    protected String misfirePolicy;

    /** 是否并发执行（0允许 1禁止） */
    protected String concurrent;

    /** 状态（0正常 1暂停） */
    protected String status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getNextValidTime() {
        return StrUtil.isNotEmpty(cronExpression) ? CronUtils.getNextExecution(cronExpression) : null;
    }
}
