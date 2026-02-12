package com.kk.mumuchat.tenant.source.controller.inner;

import com.kk.mumuchat.common.core.web.result.R;
import com.kk.mumuchat.common.log.annotation.Log;
import com.kk.mumuchat.common.log.enums.BusinessType;
import com.kk.mumuchat.common.security.annotation.InnerAuth;
import com.kk.mumuchat.tenant.api.source.feign.RemoteSourceService;
import com.kk.mumuchat.tenant.source.controller.base.BTeSourceController;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 租户服务 | 策略模块 | 数据源管理 | 内部调用 业务处理
 *
 * @author xueyi
 */
@InnerAuth
@RestController
@RequestMapping("/inner/source")
public class ITeSourceController extends BTeSourceController implements RemoteSourceService {

    @Override
    @GetMapping("/refresh")
    @Operation(summary = "刷新数据源缓存")
    @Log(title = "数据源管理", businessType = BusinessType.REFRESH)
    public R<Boolean> refreshCacheInner() {
        return super.refreshCacheInner();
    }
}