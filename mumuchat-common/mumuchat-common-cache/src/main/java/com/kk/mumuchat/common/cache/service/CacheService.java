package com.kk.mumuchat.common.cache.service;

import com.kk.mumuchat.common.core.utils.cache.CacheUtil;
import com.kk.mumuchat.common.core.utils.core.MapUtil;
import com.kk.mumuchat.common.core.utils.core.ObjectUtil;
import com.kk.mumuchat.common.core.web.entity.cache.ICusCacheService;
import com.kk.mumuchat.common.redis.constant.RedisConstants;
import com.kk.mumuchat.common.redis.service.RedisService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 自定义缓存管理服务
 *
 * @author xueyi
 **/
@Component
public class CacheService {

    @Resource
    private RedisService redisService;

    /**
     * 获取指定缓存数据对象
     *
     * @param cusCache 自定义缓存类型
     * @return 数据对象
     */
    public <T, C extends ICusCacheService> Map<String, T> getCacheMap(C cusCache) {
        return getCacheMap(cusCache.getCode(), cusCache.getIsTenant(), cusCache.getConsumer());
    }

    /**
     * 获取指定缓存数据对象
     *
     * @param cacheCode 缓存编码
     * @param isTenant  租户级缓存
     * @param consumer  缓存更新方法
     * @return 数据对象
     */
    public <T> Map<String, T> getCacheMap(String cacheCode, Boolean isTenant, Supplier<Object> consumer) {
        String key = CacheUtil.getCusCacheKey(cacheCode, isTenant);
        Map<String, T> result = redisService.getCacheMap(key);
        if (MapUtil.isEmpty(result)) {
            refreshCache(cacheCode, null, consumer);
            return redisService.getCacheMap(key);
        }
        return result;
    }

    /**
     * 获取Hash中的数据
     *
     * @param cusCache 自定义缓存类型
     * @param hKey     Hash键
     * @return Hash中的对象
     */
    public <T, C extends ICusCacheService> T getCacheMapValue(C cusCache, Serializable hKey) {
        return getCacheMapValue(cusCache.getCode(), hKey, cusCache.getIsTenant(), cusCache.getConsumer());
    }

    /**
     * 获取Hash中的数据
     *
     * @param cacheCode 缓存编码
     * @param hKey      Hash键
     * @param isTenant  租户级缓存
     * @param consumer  缓存更新方法
     * @return Hash中的对象
     */
    public <T> T getCacheMapValue(String cacheCode, Serializable hKey, Boolean isTenant, Supplier<Object> consumer) {
        if (ObjectUtil.isNull(hKey)) {
            return null;
        }
        String hKeyStr = hKey.toString();
        String key = CacheUtil.getCusCacheKey(cacheCode, isTenant);
        T result = redisService.getCacheMapValue(key, hKeyStr);
        if (ObjectUtil.isNull(result)) {
            refreshCache(cacheCode, hKeyStr, consumer);
            return redisService.getCacheMapValue(key, hKeyStr);
        }
        return result;
    }

    /**
     * 同步缓存数据
     *
     * @param cacheOperateType 缓存操作类型（REFRESH_ALL: 刷新全部, REFRESH: 刷新部分, REMOVE: 删除部分）
     * @param cacheKeyName     缓存键名
     * @param dataList         数据列表
     * @param cacheKeyFun      缓存键函数，用于从数据对象中提取键
     * @param cacheValueFun    缓存值函数，用于从数据对象中提取值
     * @param <D>              数据类型
     */
    public <D> void syncCacheData(RedisConstants.OperateType cacheOperateType, String cacheKeyName, Collection<D> dataList, Function<? super D, String> cacheKeyFun, Function<? super D, Object> cacheValueFun) {
        switch (cacheOperateType) {
            case REFRESH_ALL -> {
                redisService.deleteObject(cacheKeyName);
                redisService.refreshMapCache(cacheKeyName, dataList, cacheKeyFun, cacheValueFun);
            }
            case REFRESH -> redisService.refreshMapCache(cacheKeyName, dataList, cacheKeyFun, cacheValueFun);
            case REMOVE -> redisService.refreshMapCache(cacheKeyName, dataList, cacheKeyFun);
        }
    }

    /**
     * 更新指定类型的全量缓存
     *
     * @param cacheCode 缓存编码
     * @param hKey      Hash键
     * @param consumer  缓存更新方法
     */
    private void refreshCache(String cacheCode, String hKey, Supplier<Object> consumer) {
        Optional.ofNullable(consumer)
//                .filter(c -> {
//                    if (StrUtil.isBlank(hKey)) {
//                        return Boolean.TRUE;
//                    }
//                    boolean isNotExist = !redisService.hasKey(cacheCode);
//                    if (isNotExist) {
//                        return Boolean.TRUE;
//                    }
//                    String key = RedisConstants.CacheKey.CAPTCHA_CODE_KEY.getCacheName(cacheCode, hKey);
//                    long result = redisService.increment(key);
//                    return result < 10;
//                })
                .ifPresent(Supplier::get);
    }
}
