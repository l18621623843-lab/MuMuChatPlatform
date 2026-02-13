package com.kk.mumuchat.common.core.web.entity.cache;

import com.kk.mumuchat.common.core.utils.core.NumberUtil;
import com.kk.mumuchat.common.core.utils.core.StrUtil;

import java.util.concurrent.TimeUnit;

/**
 * 常规缓存通用规则
 *
 * @author mumuchat
 */
public interface ICacheService {

    /**
     * 获取缓存名称
     *
     * @return 缓存名称
     */
    String getCode();

    /**
     * 获取缓存过期时间
     *
     * @return 缓存过期时间
     */
    default Long getExpire() {
        return (long) NumberUtil.One;
    }

    /**
     * 获取缓存过期时间单位
     *
     * @return 缓存过期时间单位
     */
    default TimeUnit getTimeUnit() {
        return TimeUnit.DAYS;
    }

    /**
     * 获取缓存名称
     *
     * @param cacheNames 参数
     * @return 缓存名称
     */
    default String getCacheName(Object... cacheNames) {
        return StrUtil.format(getCode(), cacheNames);
    }

    /**
     * 获取目录下所有缓存名称|正常缓存名后带:*
     *
     * @param cacheNames 参数
     * @return 缓存名称
     */
    default String getCacheNameWithSuffixStar(Object... cacheNames) {
        return StrUtil.format("{}:*", StrUtil.format(getCode(), cacheNames));
    }

    /**
     * 获取目录下所有缓存名称|缓存名的最后一个变量变成*
     *
     * @param cacheNames 参数
     * @return 缓存名称
     */
    default String getCacheNameWithStar(Object... cacheNames) {
        return StrUtil.format(StrUtil.format(getCode(), cacheNames), StrUtil.ASTERISK);
    }
}

