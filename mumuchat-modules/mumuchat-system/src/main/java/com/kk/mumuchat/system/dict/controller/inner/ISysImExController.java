package com.kk.mumuchat.system.dict.controller.inner;

import com.kk.mumuchat.common.core.constant.basic.SecurityConstants;
import com.kk.mumuchat.common.core.context.SecurityContextHolder;
import com.kk.mumuchat.common.core.web.result.R;
import com.kk.mumuchat.common.log.annotation.Log;
import com.kk.mumuchat.common.log.enums.BusinessType;
import com.kk.mumuchat.common.security.annotation.InnerAuth;
import com.kk.mumuchat.system.dict.controller.base.BSysImExController;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 导入导出配置管理|内部调用 业务处理
 *
 * @author mumuchat
 */
@InnerAuth
@RestController
@RequestMapping("/inner/imExConfig")
public class ISysImExController extends BSysImExController {

    @GetMapping(value = "/sync")
    @Operation(summary = "同步配置缓存|租户数据")
    public R<Boolean> syncCacheInner() {
        return R.ok(baseService.syncCache());
    }

    @Override
    @GetMapping("/refresh")
    @Operation(summary = "刷新配置缓存|租户数据")
    @Log(title = "配置管理", businessType = BusinessType.REFRESH)
    public R<Boolean> refreshCacheInner() {
        return super.refreshCacheInner();
    }

    @GetMapping("/common/refresh")
    @Operation(summary = "刷新配置缓存|默认数据")
    @Log(title = "导入导出配置管理", businessType = BusinessType.REFRESH)
    public R<Boolean> refreshCommonCacheInner() {
        SecurityContextHolder.setEnterpriseId(SecurityConstants.COMMON_TENANT_ID.toString());
        return super.refreshCacheInner();
    }
}
