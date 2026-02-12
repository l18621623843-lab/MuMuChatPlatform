package com.kk.mumuchat.system.monitor.controller.inner;

import com.kk.mumuchat.common.core.web.result.R;
import com.kk.mumuchat.common.security.annotation.InnerAuth;
import com.kk.mumuchat.system.api.log.domain.dto.SysLoginLogDto;
import com.kk.mumuchat.system.monitor.controller.base.BSysLoginLogController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统服务|监控模块|访问日志管理|内部调用 业务处理
 *
 * @author xueyi
 */
@InnerAuth
@RestController
@RequestMapping("/inner/loginLog")
public class ISysLoginLogController extends BSysLoginLogController {

    /**
     * 新增访问日志|内部调用
     */
    @PostMapping
    public R<Boolean> addInner(@RequestBody SysLoginLogDto loginInfo) {
        return R.success(baseService.insert(loginInfo));
    }
}
