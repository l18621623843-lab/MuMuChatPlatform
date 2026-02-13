package com.kk.mumuchat.common.redis.service;

import com.kk.mumuchat.common.core.storage.INumStorageService;
import com.kk.mumuchat.common.core.storage.IStorageService;
import com.kk.mumuchat.common.core.utils.core.BooleanUtil;
import com.kk.mumuchat.common.core.utils.core.CollUtil;
import com.kk.mumuchat.common.core.utils.core.MapUtil;
import com.kk.mumuchat.common.core.utils.core.NumberUtil;
import com.kk.mumuchat.common.core.utils.core.ObjectUtil;
import com.kk.mumuchat.common.core.utils.core.StrUtil;
import com.kk.mumuchat.common.redis.configure.FastJson2JsonRedisSerializer;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.types.Expiration;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * redis 基础缓存管理实现类
 *
 * @author mumuchat
 **/
@SuppressWarnings(value = {"unchecked", "rawtypes"})
public abstract class RedisBaseService implements IStorageService, INumStorageService {

    /**
     * 获取RedisTemplate
     *
     * @return RedisTemplate
     */
    public abstract RedisTemplate getRedisTemplate();

    @Override
    public <T> void setCacheObject(final String key, final T value) {
        getRedisTemplate().opsForValue().set(key, value);
    }

    @Override
    public <T> void setPipeCacheObject(final Map<String, T> cacheMap, final long timeout, final TimeUnit timeUnit) {
        Expiration expiration = Expiration.from(timeout, timeUnit);
        FastJson2JsonRedisSerializer serializer = new FastJson2JsonRedisSerializer(Object.class);
        getRedisTemplate().executePipelined((RedisCallback<Void>) connection -> {
            // 开始批处理
            connection.multi();
            cacheMap.forEach((key, value) -> {
                byte[] keyBytes = key.getBytes();
                byte[] valueBytes = serializer.serialize(value);
                connection.set(keyBytes, valueBytes, expiration, RedisStringCommands.SetOption.UPSERT);
            });
            // 执行批处理
            connection.exec();
            return null;
        });
    }

    @Override
    public <T> void setExpireCacheObject(final String key, final T value) {
        setCacheObject(key, value, (long) NumberUtil.Eight, TimeUnit.HOURS);
    }

    @Override
    public <T> void setCacheObject(final String key, final T value, final Long timeout, final TimeUnit timeUnit) {
        getRedisTemplate().opsForValue().set(key, value, timeout, timeUnit);
    }

    @Override
    public <T> Boolean setCacheObjectIfAbsent(final String key, final T value, final Long timeout, final TimeUnit timeUnit) {
        return getRedisTemplate().opsForValue().setIfAbsent(key, value, timeout, timeUnit);
    }

    @Override
    public boolean expire(final String key, final long timeout) {
        return expire(key, timeout, TimeUnit.SECONDS);
    }

    @Override
    public Boolean expire(final String key, final long timeout, final TimeUnit unit) {
        return getRedisTemplate().expire(key, timeout, unit);
    }

    @Override
    public Long getExpire(final String key) {
        return getRedisTemplate().getExpire(key);
    }

    @Override
    public Long getExpire(final String key, final TimeUnit unit) {
        return getRedisTemplate().getExpire(key, unit);
    }

    @Override
    public Boolean hasKey(String key) {
        return getRedisTemplate().hasKey(key);
    }

    public <T> ValueOperations<String, T> getCacheOperation() {
        return (ValueOperations<String, T>) getRedisTemplate().opsForValue();
    }

    @Override
    public <T> T getCacheObject(final String key) {
        ValueOperations<String, T> operation = getRedisTemplate().opsForValue();
        return operation.get(key);
    }

    @Override
    public Boolean deleteObject(final String key) {
        return getRedisTemplate().delete(key);
    }

    @Override
    public Long deleteObject(final Collection collection) {
        return getRedisTemplate().delete(collection);
    }

    @Override
    public <T> long setCacheList(final String key, final List<T> dataList) {
        Long count = getRedisTemplate().opsForList().rightPushAll(key, dataList);
        return count == null ? 0 : count;
    }

    @Override
    public <T> long setExpireCacheList(final String key, final List<T> dataList) {
        return setCacheList(key, dataList, NumberUtil.Eight, TimeUnit.HOURS);
    }

    @Override
    public <T> long setCacheList(final String key, final List<T> dataList, final long timeout, final TimeUnit unit) {
        Long count = getRedisTemplate().opsForList().rightPushAll(key, dataList);
        if (ObjectUtil.isNotNull(count)) {
            expire(key, timeout, unit);
        }
        return count == null ? 0 : count;
    }

    @Override
    public <T> List<T> getCacheList(final String key) {
        return getRedisTemplate().opsForList().range(key, 0, -1);
    }

    @Override
    public <T> void setCacheSet(final String key, final Set<T> dataSet) {
        BoundSetOperations<String, T> setOperation = getRedisTemplate().boundSetOps(key);
        for (T t : dataSet) {
            setOperation.add(t);
        }
    }

    @Override
    public <T> void setCacheSet(final String key, final Set<T> dataSet, final long timeout, final TimeUnit unit) {
        BoundSetOperations<String, T> setOperation = getRedisTemplate().boundSetOps(key);
        for (T t : dataSet) {
            setOperation.add(t);
        }
        expire(key, timeout, unit);
    }

    @Override
    public <T> Set<T> getCacheSet(final String key) {
        return getRedisTemplate().opsForSet().members(key);
    }

    @Override
    public <T> void setCacheMap(final String key, final Map<String, T> dataMap) {
        if (dataMap != null) {
            getRedisTemplate().opsForHash().putAll(key, dataMap);
        }
    }

    @Override
    public <T> void setExpireCacheMap(final String key, final Map<String, T> dataMap) {
        setCacheMap(key, dataMap, NumberUtil.Eight, TimeUnit.HOURS);
    }

    @Override
    public <T> void setCacheMap(final String key, final Map<String, T> dataMap, final long timeout, final TimeUnit unit) {
        if (dataMap != null) {
            getRedisTemplate().opsForHash().putAll(key, dataMap);
            expire(key, timeout, unit);
        }
    }

    @Override
    public <T> Map<String, T> getCacheMap(final String key) {
        return getRedisTemplate().opsForHash().entries(key);
    }

    @Override
    public <T> void setCacheMapValue(final String key, final String hKey, final T value) {
        getRedisTemplate().opsForHash().put(key, hKey, value);
    }

    @Override
    public <T> T getCacheMapValue(final String key, final String hKey) {
        HashOperations<String, String, T> opsForHash = getRedisTemplate().opsForHash();
        return opsForHash.get(key, hKey);
    }

    @Override
    public <T> List<T> getMultiCacheMapValue(final String key, final Collection<Object> hKeys) {
        return getRedisTemplate().opsForHash().multiGet(key, hKeys);
    }

    @Override
    public boolean deleteCacheMapValue(final String key, final String hKey) {
        return getRedisTemplate().opsForHash().delete(key, hKey) > 0;
    }

    @Override
    public boolean deleteCacheMapValue(final String key, final Object[] hKey) {
        return getRedisTemplate().opsForHash().delete(key, hKey) > 0;
    }

    // --------------------- ZSet 方法实现 ---------------------

    @Override
    public <T> Boolean zAdd(String key, T value, double score) {
        return getRedisTemplate().opsForZSet().add(key, value, score);
    }

    @Override
    public <T> Long zRem(String key, T value) {
        return getRedisTemplate().opsForZSet().remove(key, value);
    }

    @Override
    public <T> Long zRank(String key, T value) {
        return getRedisTemplate().opsForZSet().rank(key, value);
    }

    @Override
    public <T> Long zReverseRank(String key, T value) {
        return getRedisTemplate().opsForZSet().reverseRank(key, value);
    }

    @Override
    public <T> Set<T> zRange(String key, long start, long end) {
        return (Set<T>) getRedisTemplate().opsForZSet().range(key, start, end);
    }

    @Override
    public <T> Set<T> zReverseRange(String key, long start, long end) {
        return (Set<T>) getRedisTemplate().opsForZSet().reverseRange(key, start, end);
    }

    @Override
    public Long zRemRangeByRank(String key, long start, long end) {
        return getRedisTemplate().opsForZSet().removeRange(key, start, end);
    }

    @Override
    public Long zRemRangeByScore(String key, double min, double max) {
        return getRedisTemplate().opsForZSet().removeRangeByScore(key, min, max);
    }

    @Override
    public Long zCard(String key) {
        return getRedisTemplate().opsForZSet().size(key);
    }

    @Override
    public <T> Double zScore(String key, T value) {
        return getRedisTemplate().opsForZSet().score(key, value);
    }

    @Override
    public <T> Double zIncrementScore(String key, T value, double delta) {
        return getRedisTemplate().opsForZSet().incrementScore(key, value, delta);
    }

    // ===== 计数 方法实现 =====

    @Override
    public Long increment(String key, long delta) {
        return Optional.ofNullable(getRedisTemplate().opsForValue().increment(key, delta)).orElse(0L);
    }

    @Override
    public Long increment(String key, long delta, long defaultValue) {
        if (BooleanUtil.isFalse(hasKey(key))) {
            getRedisTemplate().opsForValue().setIfAbsent(key, defaultValue);
        }
        return Optional.ofNullable(getRedisTemplate().opsForValue().increment(key, delta)).orElse(defaultValue + delta);
    }

    @Override
    public Long decrement(String key, long delta) {
        return Optional.ofNullable(getRedisTemplate().opsForValue().decrement(key, delta)).orElse(0L);
    }

    @Override
    public Long decrement(String key, long delta, long defaultValue) {
        if (BooleanUtil.isFalse(hasKey(key))) {
            getRedisTemplate().opsForValue().setIfAbsent(key, defaultValue);
        }
        return Optional.ofNullable(getRedisTemplate().opsForValue().decrement(key, delta)).orElse(defaultValue + delta);
    }

    // --------------------- 通用缓存实现 ---------------------

    @Override
    public Collection<String> keys(final String pattern) {
        return getRedisTemplate().keys(pattern);
    }

    @Override
    public <T> void refreshListCache(String optionKey, List<T> dataList) {
        setExpireCacheList(optionKey, dataList);
    }

    @Override
    public <T> void refreshMapCache(String mapKey, Collection<T> cacheList, Function<? super T, String> keyGet) {
        refreshMapCache(mapKey, cacheList, keyGet, null);
    }

    @Override
    public <T, K> void refreshMapCache(String mapKey, Collection<T> cacheList, Function<? super T, String> keyGet, Function<? super T, K> valueGet) {
        // 新增/修改操作
        if (ObjectUtil.isNotNull(valueGet)) {
            Map<String, K> resultMap = new HashMap<>();
            if (CollUtil.isNotEmpty(cacheList)) {
                resultMap = cacheList.stream()
                        .filter(item -> {
                            String key = keyGet.apply(item);
                            if (StrUtil.isBlank(key)) {
                                return Boolean.FALSE;
                            }
                            return ObjectUtil.isNotNull(valueGet.apply(item));
                        }).collect(Collectors.toMap(keyGet, valueGet, (v1, v2) -> v1));
            }
            if (MapUtil.isNotEmpty(resultMap)) {
                setExpireCacheMap(mapKey, resultMap);
            }
        }
        // 删除操作
        else {
            Set<String> keys = cacheList.stream()
                    .map(item -> {
                        String key = keyGet.apply(item);
                        if (StrUtil.isBlank(key)) {
                            return null;
                        }
                        return key;
                    }).filter(ObjectUtil::isNotNull).collect(Collectors.toSet());
            if (CollUtil.isNotEmpty(keys)) {
                deleteCacheMapValue(mapKey, keys.toArray());
            }
        }
    }
}
