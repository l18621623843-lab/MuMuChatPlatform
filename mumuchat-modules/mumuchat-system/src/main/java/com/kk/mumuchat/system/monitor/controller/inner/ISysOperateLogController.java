package com.kk.mumuchat.system.monitor.controller.inner;

import com.kk.mumuchat.common.core.web.result.R;
import com.kk.mumuchat.common.security.annotation.InnerAuth;
import com.kk.mumuchat.system.api.log.domain.dto.SysOperateLogDto;
import com.kk.mumuchat.system.monitor.controller.base.BSysOperateLogController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统服务|监控模块|操作日志管理|内部调用 业务处理
 *
 * @author mumuchat
 */
@InnerAuth
@RestController
@RequestMapping("/inner/operateLog")
public class ISysOperateLogController extends BSysOperateLogController {

    /**
     * 新增操作日志|内部调用
     */
    @PostMapping
    public R<Boolean> addInner(@RequestBody SysOperateLogDto operateLog) {
        baseService.insert(operateLog);
        return R.ok();
    }
}
