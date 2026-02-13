package com.kk.mumuchat.job.controller.admin;

import com.kk.mumuchat.common.core.web.result.AjaxResult;
import com.kk.mumuchat.common.core.web.validate.V_A;
import com.kk.mumuchat.common.core.web.validate.V_E;
import com.kk.mumuchat.common.log.annotation.Log;
import com.kk.mumuchat.common.log.enums.BusinessType;
import com.kk.mumuchat.common.security.annotation.AdminAuth;
import com.kk.mumuchat.job.api.domain.dto.SysJobDto;
import com.kk.mumuchat.job.api.domain.query.SysJobQuery;
import com.kk.mumuchat.job.controller.base.BSysJobController;
import io.swagger.v3.oas.annotations.Operation;
import org.quartz.SchedulerException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.List;

/**
 * 定时任务|调度任务管理 | 管理端 业务处理
 *
 * @author mumuchat
 */
@AdminAuth
@RestController
@RequestMapping("/admin/job")
public class ASysJobController extends BSysJobController {

    @Override
    @GetMapping("/list")
    @Operation(summary = "查询定时任务列表")
    @PreAuthorize("@ss.hasAuthority(@Auth.SCHEDULE_JOB_LIST)")
    public AjaxResult list(SysJobQuery job) {
        return super.list(job);
    }

    @Override
    @GetMapping(value = "/{id}")
    @Operation(summary = "查询调度任务详细")
    @PreAuthorize("@ss.hasAuthority(@Auth.SCHEDULE_JOB_SINGLE)")
    public AjaxResult getInfo(@PathVariable Serializable id) {
        return super.getInfo(id);
    }

    @GetMapping("/getInnerServiceMapping")
    @Operation(summary = "获取内部服务映射列表")
    public AjaxResult getInnerServiceMapping() {
        return success(baseService.selectInnerServiceMapping());
    }

    @Override
    @PostMapping
    @Operation(summary = "调度任务新增")
    @PreAuthorize("@ss.hasAuthority(@Auth.SCHEDULE_JOB_ADD)")
    @Log(title = "调度任务管理", businessType = BusinessType.INSERT)
    public AjaxResult add(@Validated({V_A.class}) @RequestBody SysJobDto job) {
        return super.add(job);
    }

    @Override
    @PutMapping
    @Operation(summary = "调度任务修改")
    @PreAuthorize("@ss.hasAuthority(@Auth.SCHEDULE_JOB_EDIT)")
    @Log(title = "调度任务管理", businessType = BusinessType.UPDATE)
    public AjaxResult edit(@Validated({V_E.class}) @RequestBody SysJobDto job) {
        return super.edit(job);
    }

    @Override
    @PutMapping("/status")
    @Operation(summary = "调度任务修改状态")
    @PreAuthorize("@ss.hasAnyAuthority(@Auth.SCHEDULE_JOB_EDIT, @Auth.SCHEDULE_JOB_ES)")
    @Log(title = "调度任务管理", businessType = BusinessType.UPDATE_STATUS)
    public AjaxResult editStatus(@RequestBody SysJobDto job) {
        return super.editStatus(job);
    }

    @GetMapping("/run/{id}")
    @Operation(summary = "定时任务立即执行一次")
    @PreAuthorize("@ss.hasAuthority(@Auth.SCHEDULE_JOB_EDIT)")
    @Log(title = "定时任务 - 执行一次", businessType = BusinessType.UPDATE)
    public AjaxResult run(@PathVariable Long id) throws SchedulerException {
        return baseService.run(id) ? success() : error("任务不存在或已过期！");
    }

    @Override
    @DeleteMapping("/batch/{idList}")
    @Operation(summary = "调度任务批量删除")
    @PreAuthorize("@ss.hasAuthority(@Auth.SCHEDULE_JOB_DEL)")
    @Log(title = "调度任务管理", businessType = BusinessType.DELETE)
    public AjaxResult batchRemove(@PathVariable List<Long> idList) {
        return super.batchRemove(idList);
    }
}