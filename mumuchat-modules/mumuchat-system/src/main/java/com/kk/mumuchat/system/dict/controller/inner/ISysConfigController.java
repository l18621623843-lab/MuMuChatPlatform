package com.kk.mumuchat.system.dict.controller.inner;

import com.kk.mumuchat.common.core.constant.basic.BaseConstants;
import com.kk.mumuchat.common.core.constant.basic.SecurityConstants;
import com.kk.mumuchat.common.core.context.SecurityContextHolder;
import com.kk.mumuchat.common.core.web.result.R;
import com.kk.mumuchat.common.core.web.validate.V_E;
import com.kk.mumuchat.common.log.annotation.Log;
import com.kk.mumuchat.common.log.enums.BusinessType;
import com.kk.mumuchat.common.security.annotation.InnerAuth;
import com.kk.mumuchat.system.api.dict.domain.dto.SysConfigDto;
import com.kk.mumuchat.system.dict.controller.base.BSysConfigController;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统服务|字典模块|参数管理|内部调用 业务处理
 *
 * @author mumuchat
 */
@InnerAuth
@RestController
@RequestMapping("/inner/config")
public class ISysConfigController extends BSysConfigController {

    @GetMapping(value = "/sync")
    @Operation(summary = "同步参数缓存|租户数据")
    public R<Boolean> syncCacheInner() {
        return R.ok(baseService.syncCache());
    }

    @Override
    @GetMapping("/refresh")
    @Operation(summary = "刷新参数缓存|租户数据")
    public R<Boolean> refreshCacheInner() {
        return super.refreshCacheInner();
    }

    @GetMapping("/common/refresh")
    @Operation(summary = "刷新参数缓存|默认数据")
    public R<Boolean> refreshCommonCacheInner() {
        SecurityContextHolder.setEnterpriseId(SecurityConstants.COMMON_TENANT_ID.toString());
        return super.refreshCacheInner();
    }

    @GetMapping("/code/{code}")
    @Operation(summary = "查询参数对象")
    public R<SysConfigDto> getConfigByCodeInner(@PathVariable("code") String code) {
        return R.ok(baseService.selectConfigByCode(code));
    }

    @PutMapping
    @Operation(summary = "参数修改")
    @Log(title = "参数管理", businessType = BusinessType.UPDATE)
    public R<Boolean> editInner(@Validated({V_E.class}) @RequestBody SysConfigDto config) {
        config.initOperate(BaseConstants.Operate.EDIT);
        AEHandle(config.getOperate(), config);
        return R.success(baseService.update(config));
    }
}
