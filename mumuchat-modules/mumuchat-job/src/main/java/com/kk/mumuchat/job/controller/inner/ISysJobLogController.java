package com.kk.mumuchat.job.controller.inner;

import com.kk.mumuchat.common.core.web.result.R;
import com.kk.mumuchat.common.security.annotation.InnerAuth;
import com.kk.mumuchat.job.api.domain.dto.SysJobLogDto;
import com.kk.mumuchat.job.api.feign.RemoteJobLogService;
import com.kk.mumuchat.job.controller.base.BSysJobLogController;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 定时任务|调度日志管理 | 内部调用 业务处理
 *
 * @author xueyi
 */
@InnerAuth
@RestController
@RequestMapping("/inner/job/log")
public class ISysJobLogController extends BSysJobLogController implements RemoteJobLogService {

    @Override
    @PostMapping
    @Operation(summary = "新增调度日志")
    public R<Boolean> addInner(@RequestBody SysJobLogDto jobLog) {
        baseService.insert(jobLog);
        return R.ok();
    }
}
