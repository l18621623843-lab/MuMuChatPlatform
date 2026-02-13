package com.kk.mumuchat.tenant.api.tenant.constant;

import com.kk.mumuchat.common.core.utils.core.SpringUtil;
import com.kk.mumuchat.common.core.web.entity.cache.ICusCacheService;
import com.kk.mumuchat.tenant.api.tenant.feign.RemoteTenantService;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Supplier;

/**
 * 租户通用常量
 *
 * @author mumuchat
 */
public class TenantConstants {

    /** 缓存类型 */
    @Getter
    @AllArgsConstructor
    public enum CacheType implements ICusCacheService {

        TE_TENANT_KEY("system:tenant", false, "租户", () -> SpringUtil.getBean(RemoteTenantService.class).refreshCacheInner()),
        TE_ENTERPRISE_SYSTEM_NAME_KEY("system:enterpriseSystemName", false, "租户账号", () -> SpringUtil.getBean(RemoteTenantService.class).refreshCacheInner());

        private final String code;
        private final Boolean isTenant;
        private final String info;
        private final Supplier<Object> consumer;
    }
}