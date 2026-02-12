package com.kk.mumuchat.common.core.storage;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 计数缓存管理器
 *
 * @author xueyi
 */
public interface INumStorageService {

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key   Redis键
     * @param value 缓存的值
     */
    <T> void setCacheObject(final String key, final T value);

    /**
     * 管道式缓存基本的对象
     *
     * @param cacheMap 待缓存Map
     * @param timeout  时间
     * @param timeUnit 时间颗粒度
     */
    <T> void setPipeCacheObject(final Map<String, T> cacheMap, final long timeout, final TimeUnit timeUnit);

    /**
     * 缓存基本的对象，Integer、String、实体类等 | 默认八小时
     *
     * @param key   Redis键
     * @param value 缓存的值
     */
    <T> void setExpireCacheObject(final String key, final T value);

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key      Redis键
     * @param value    缓存的值
     * @param timeout  时间
     * @param timeUnit 时间颗粒度
     */
    <T> void setCacheObject(final String key, final T value, final Long timeout, final TimeUnit timeUnit);

    /**
     * 缓存基本的对象，Integer、String、实体类等 | 如果不存在则设置
     *
     * @param key      Redis键
     * @param value    缓存的值
     * @param timeout  时间
     * @param timeUnit 时间颗粒度
     * @return 结果
     */
    <T> Boolean setCacheObjectIfAbsent(final String key, final T value, final Long timeout, final TimeUnit timeUnit);

    /**
     * 设置有效时间
     *
     * @param key     Redis键
     * @param timeout 超时时间
     * @return true=设置成功；false=设置失败
     */
    boolean expire(final String key, final long timeout);

    /**
     * 设置有效时间
     *
     * @param key     Redis键
     * @param timeout 超时时间
     * @param unit    时间单位
     * @return true=设置成功；false=设置失败
     */
    Boolean expire(final String key, final long timeout, final TimeUnit unit);

    /**
     * 获取有效时间
     *
     * @param key Redis键
     * @return 有效时间
     */
    Long getExpire(final String key);

    /**
     * 获取有效时间
     *
     * @param key  Redis键
     * @param unit 时间单位
     * @return 有效时间
     */
    Long getExpire(final String key, final TimeUnit unit);

    /**
     * 判断 key是否存在
     *
     * @param key Redis键
     * @return true 存在 false不存在
     */
    Boolean hasKey(String key);

    /**
     * 获得缓存的基本对象
     *
     * @param key Redis键
     * @return 缓存键值对应的数据
     */
    <T> T getCacheObject(final String key);

    /**
     * 删除单个对象
     *
     * @param key Redis键
     * @return true=删除成功；false=删除失败
     */
    Boolean deleteObject(final String key);

    /**
     * 原子性地自增1
     *
     * @param key Redis键
     * @return 自增后的结果
     */
    default Long increment(String key) {
        return increment(key, 1);
    }

    /**
     * 原子性地增加指定键的数值
     *
     * @param key   Redis键
     * @param delta 增量值
     * @return 增加后的结果
     */
    Long increment(String key, long delta);

    /**
     * 原子性地增加指定键的数值，如果不存在则先设置默认值
     *
     * @param key          Redis键
     * @param delta        增量值
     * @param defaultValue 如果键不存在，则设置的初始值
     * @return 增加后的结果
     */
    Long increment(String key, long delta, long defaultValue);

    /**
     * 原子性地自减1
     *
     * @param key Redis键
     * @return 自减后的结果
     */
    default Long decrement(String key) {
        return decrement(key, 1);
    }

    /**
     * 原子性地减少指定键的数值
     *
     * @param key   Redis键
     * @param delta 减量值
     * @return 减少后的结果
     */
    Long decrement(String key, long delta);

    /**
     * 原子性地减少指定键的数值，如果不存在则先设置默认值
     *
     * @param key          Redis键
     * @param delta        减量值（正数）
     * @param defaultValue 如果键不存在，则设置的初始值
     * @return 减少后的结果
     */
    Long decrement(String key, long delta, long defaultValue);
}
