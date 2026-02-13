package com.kk.mumuchat.common.core.web.entity.cache;

import java.util.function.Supplier;

/**
 * 自定义缓存管理规则
 *
 * @author mumuchat
 */
public interface ICusCacheService extends ICacheService {

    /**
     * 是否是租户级缓存
     *
     * @return 是否是租户级缓存
     */
    Boolean getIsTenant();

    /**
     * 获取缓存刷新方法
     *
     * @return 缓存刷新方法
     */
    Supplier<Object> getConsumer();
}

