package com.kk.mumuchat.tenant.api.source.constant;

import com.kk.mumuchat.common.core.utils.core.SpringUtil;
import com.kk.mumuchat.common.core.web.entity.cache.ICusCacheService;
import com.kk.mumuchat.tenant.api.source.feign.RemoteSourceService;
import com.kk.mumuchat.tenant.api.source.feign.RemoteStrategyService;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Supplier;

/**
 * 数据源通用常量
 *
 * @author mumuchat
 */
public class SourceConstants {

    /** 缓存类型 */
    @Getter
    @AllArgsConstructor
    public enum CacheType implements ICusCacheService {

        TE_STRATEGY_KEY("system:strategy", false, "源策略组", () -> SpringUtil.getBean(RemoteStrategyService.class).refreshCacheInner()),
        TE_SOURCE_KEY("system:source", false, "数据源", () -> SpringUtil.getBean(RemoteSourceService.class).refreshCacheInner());

        private final String code;
        private final Boolean isTenant;
        private final String info;
        private final Supplier<Object> consumer;

    }
}