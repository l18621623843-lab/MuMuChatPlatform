package com.kk.mumuchat.tenant.source.controller.inner;

import com.kk.mumuchat.common.core.web.result.R;
import com.kk.mumuchat.common.security.annotation.InnerAuth;
import com.kk.mumuchat.tenant.api.source.feign.RemoteStrategyService;
import com.kk.mumuchat.tenant.source.controller.base.BTeStrategyController;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 租户服务 | 策略模块 | 源策略管理 | 内部调用 业务处理
 *
 * @author mumuchat
 */
@InnerAuth
@RestController
@RequestMapping("/inner/strategy")
public class ITeStrategyController extends BTeStrategyController implements RemoteStrategyService {

    @Override
    @GetMapping("/refresh")
    @Operation(summary = "刷新源策略缓存")
    public R<Boolean> refreshCacheInner() {
        return super.refreshCacheInner();
    }
}