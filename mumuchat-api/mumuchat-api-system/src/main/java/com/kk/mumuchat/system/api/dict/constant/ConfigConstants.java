package com.kk.mumuchat.system.api.dict.constant;

import com.kk.mumuchat.common.core.utils.core.SpringUtil;
import com.kk.mumuchat.common.core.web.entity.cache.ICacheService;
import com.kk.mumuchat.common.core.web.entity.cache.ICusCacheService;
import com.kk.mumuchat.system.api.dict.feign.RemoteConfigService;
import com.kk.mumuchat.system.api.dict.feign.RemoteDictService;
import com.kk.mumuchat.system.api.dict.feign.RemoteImExService;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Supplier;

/**
 * 字典配置通用常量
 *
 * @author xueyi
 */
public class ConfigConstants {

    /** 缓存类型 */
    @Getter
    @AllArgsConstructor
    public enum CacheType implements ICusCacheService {

        ROUTE_DICT_KEY("system:dict:route", false, "字典路由", () -> SpringUtil.getBean(RemoteDictService.class).refreshCommonCacheInner()),
        SYS_DICT_KEY("system:dict:tenant", true, "租户字典", () -> SpringUtil.getBean(RemoteDictService.class).refreshCacheInner()),
        TE_DICT_KEY("system:dict:tenant", true, "通用字典", () -> SpringUtil.getBean(RemoteDictService.class).refreshCommonCacheInner()),
        ROUTE_CONFIG_KEY("system:config:route", false, "参数路由", () -> SpringUtil.getBean(RemoteConfigService.class).refreshCommonCacheInner()),
        SYS_CONFIG_KEY("system:config:tenant", true, "租户参数", () -> SpringUtil.getBean(RemoteConfigService.class).refreshCacheInner()),
        TE_CONFIG_KEY("system:config:tenant", true, "通用参数", () -> SpringUtil.getBean(RemoteConfigService.class).refreshCommonCacheInner()),
        ROUTE_IM_EX_KEY("system:im_ex:route", false, "导入导出配置路由", () -> SpringUtil.getBean(RemoteImExService.class).refreshCommonCacheInner()),
        SYS_IM_EX_KEY("system:im_ex:tenant", true, "租户导入导出配置", () -> SpringUtil.getBean(RemoteImExService.class).refreshCacheInner()),
        TE_IM_EX_KEY("system:im_ex:tenant", true, "通用导入导出配置", () -> SpringUtil.getBean(RemoteImExService.class).refreshCommonCacheInner());

        private final String code;
        private final Boolean isTenant;
        private final String info;
        private final Supplier<Object> consumer;

    }

    /** 缓存键 */
    @Getter
    @AllArgsConstructor
    public enum CacheKey implements ICacheService {

        DICT_KEY("system:dict:{}", "字典缓存"),
        CONFIG_KEY("system:config:{}", "参数缓存"),
        IM_EX_KEY("system:im_ex:{}", "导入导出配置缓存");

        private final String code;
        private final String info;

    }
}