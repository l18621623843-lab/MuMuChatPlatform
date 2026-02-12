package com.kk.mumuchat.tenant.tenant.controller.inner;

import com.kk.mumuchat.common.core.utils.core.NumberUtil;
import com.kk.mumuchat.common.core.web.result.R;
import com.kk.mumuchat.common.core.web.validate.V_A;
import com.kk.mumuchat.common.log.annotation.Log;
import com.kk.mumuchat.common.log.enums.BusinessType;
import com.kk.mumuchat.common.security.annotation.InnerAuth;
import com.kk.mumuchat.tenant.tenant.controller.base.BTeTenantController;
import com.kk.mumuchat.tenant.tenant.domain.dto.TeTenantRegister;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 租户服务 | 租户模块 | 租户管理 | 内部调用 业务处理
 *
 * @author xueyi
 */
@InnerAuth
@RestController
@RequestMapping("/inner/tenant")
public class ITeTenantController extends BTeTenantController {

    @Override
    @GetMapping("/refresh")
    @Operation(summary = "刷新租户缓存")
    @Log(title = "租户管理", businessType = BusinessType.REFRESH)
    public R<Boolean> refreshCacheInner() {
        return super.refreshCacheInner();
    }

    @InnerAuth
    @PostMapping("/register")
    @Operation(summary = "租户新增")
    @PreAuthorize("@ss.hasAuthority(@Auth.TE_TENANT_ADD)")
    @Log(title = "租户管理", businessType = BusinessType.INSERT)
    public R<Boolean> addInner(@Validated({V_A.class}) @RequestBody TeTenantRegister tenantRegister) {
        registerValidated(tenantRegister);
        return R.ok(baseService.insert(tenantRegister) > NumberUtil.Zero);
    }
}