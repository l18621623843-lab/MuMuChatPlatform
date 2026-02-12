package com.kk.mumuchat.job.controller.admin;

import com.kk.mumuchat.common.core.web.result.AjaxResult;
import com.kk.mumuchat.common.log.annotation.Log;
import com.kk.mumuchat.common.log.enums.BusinessType;
import com.kk.mumuchat.common.security.annotation.AdminAuth;
import com.kk.mumuchat.job.api.domain.query.SysJobLogQuery;
import com.kk.mumuchat.job.controller.base.BSysJobLogController;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;

/**
 * 定时任务|调度日志管理 | 管理端 业务处理
 *
 * @author xueyi
 */
@AdminAuth
@RestController
@RequestMapping("/admin/job/log")
public class ASysJobLogController extends BSysJobLogController {

    @Override
    @GetMapping("/list")
    @Operation(summary = "查询调度日志列表")
    @PreAuthorize("@ss.hasAuthority(@Auth.SCHEDULE_JOB_LOG)")
    public AjaxResult list(SysJobLogQuery jobLog) {
        return super.list(jobLog);
    }

    @Override
    @GetMapping(value = "/{id}")
    @Operation(summary = "查询调度日志详细")
    @PreAuthorize("@ss.hasAuthority(@Auth.SCHEDULE_JOB_LOG)")
    public AjaxResult getInfo(@PathVariable Serializable id) {
        return super.getInfo(id);
    }

    @DeleteMapping("/clean")
    @Operation(summary = "清空调度日志")
    @PreAuthorize("@ss.hasAuthority(@Auth.SCHEDULE_JOB_DEL)")
    @Log(title = "调度日志管理", businessType = BusinessType.CLEAN)
    public AjaxResult clean() {
        baseService.cleanLog();
        return success();
    }
}
