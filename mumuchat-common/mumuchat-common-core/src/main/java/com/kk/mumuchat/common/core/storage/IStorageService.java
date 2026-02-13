package com.kk.mumuchat.common.core.storage;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * 缓存管理器
 *
 * @author mumuchat
 */
public interface IStorageService {

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
     * 删除集合对象
     *
     * @param collection 多个对象
     * @return 结果
     */
    Long deleteObject(final Collection collection);

    /**
     * 缓存List数据
     *
     * @param key      Redis键
     * @param dataList 待缓存的List数据
     * @return 缓存的对象
     */
    <T> long setCacheList(final String key, final List<T> dataList);

    /**
     * 缓存List数据 | 默认八小时
     *
     * @param key      Redis键
     * @param dataList 待缓存的List数据
     * @return 缓存的对象
     */
    <T> long setExpireCacheList(final String key, final List<T> dataList);

    /**
     * 缓存List数据
     *
     * @param key      Redis键
     * @param dataList 待缓存的List数据
     * @param timeout  超时时间
     * @param unit     时间单位
     * @return 缓存的对象
     */
    <T> long setCacheList(final String key, final List<T> dataList, final long timeout, final TimeUnit unit);

    /**
     * 获得缓存的list对象
     *
     * @param key Redis键
     * @return 缓存键值对应的数据
     */
    <T> List<T> getCacheList(final String key);

    /**
     * 缓存Set
     *
     * @param key     Redis键
     * @param dataSet 缓存的数据
     */
    <T> void setCacheSet(final String key, final Set<T> dataSet);

    /**
     * 缓存Set
     *
     * @param key     Redis键
     * @param dataSet 缓存的数据
     * @param timeout 超时时间
     * @param unit    时间单位
     */
    <T> void setCacheSet(final String key, final Set<T> dataSet, final long timeout, final TimeUnit unit);

    /**
     * 获得缓存的set
     *
     * @param key Redis键
     * @return 集合
     */
    <T> Set<T> getCacheSet(final String key);

    /**
     * 缓存Map
     *
     * @param key     Redis键
     * @param dataMap map
     */
    <T> void setCacheMap(final String key, final Map<String, T> dataMap);

    /**
     * 缓存Map | 默认八小时
     *
     * @param key     Redis键
     * @param dataMap map
     */
    <T> void setExpireCacheMap(final String key, final Map<String, T> dataMap);

    /**
     * 缓存Map
     *
     * @param key     Redis键
     * @param dataMap map
     * @param timeout 超时时间
     * @param unit    时间单位
     */
    <T> void setCacheMap(final String key, final Map<String, T> dataMap, final long timeout, final TimeUnit unit);

    /**
     * 获得缓存的Map
     *
     * @param key Redis键
     * @return 哈希
     */
    <T> Map<String, T> getCacheMap(final String key);

    /**
     * 往Hash中存入数据
     *
     * @param key   Redis键
     * @param hKey  Hash键
     * @param value 值
     */
    <T> void setCacheMapValue(final String key, final String hKey, final T value);

    /**
     * 获取Hash中的数据
     *
     * @param key  Redis键
     * @param hKey Hash键
     * @return Hash中的对象
     */
    <T> T getCacheMapValue(final String key, final String hKey);

    /**
     * 获取多个Hash中的数据
     *
     * @param key   Redis键
     * @param hKeys Hash键集合
     * @return Hash对象集合
     */
    <T> List<T> getMultiCacheMapValue(final String key, final Collection<Object> hKeys);

    /**
     * 删除Hash中的某条数据
     *
     * @param key  Redis键
     * @param hKey Hash键
     * @return 是否成功
     */
    boolean deleteCacheMapValue(final String key, final String hKey);

    /**
     * 删除Hash中的某条数据
     *
     * @param key  Redis键
     * @param hKey Hash键
     * @return 是否成功
     */
    boolean deleteCacheMapValue(final String key, final Object[] hKey);
    /**
     * 向 ZSet 添加一个成员
     *
     * @param key   键
     * @param value 值（字符串或 JSON）
     * @param score 排序分值
     * @return 是否成功
     */
    <T> Boolean zAdd(String key, T value, double score);

    /**
     * 从 ZSet 中删除指定成员
     *
     * @param key   键
     * @param value 成员对象
     * @return 删除数量
     */
    <T> Long zRem(String key, T value);

    /**
     * 获取 ZSet 成员的排名（从小到大排序）
     *
     * @param key   键
     * @param value 值
     * @return 排名（从0开始），不存在则返回 null
     */
    <T> Long zRank(String key, T value);

    /**
     * 获取 ZSet 成员的排名（从大到小排序）
     *
     * @param key   键
     * @param value 值
     * @return 排名（从0开始），不存在则返回 null
     */
    <T> Long zReverseRank(String key, T value);

    /**
     * 获取 ZSet 的指定区间成员（按 score 升序）
     *
     * @param key   键
     * @param start 起始索引
     * @param end   结束索引
     * @return 成员集合
     */
    <T> Set<T> zRange(String key, long start, long end);

    /**
     * 获取 ZSet 的指定区间成员（按 score 降序）
     *
     * @param key   键
     * @param start 起始索引
     * @param end   结束索引
     * @return 成员集合
     */
    <T> Set<T> zReverseRange(String key, long start, long end);

    /**
     * 删除 ZSet 中指定排名范围的成员（升序）
     *
     * @param key   键
     * @param start 开始排名
     * @param end   结束排名
     * @return 删除的数量
     */
    Long zRemRangeByRank(String key, long start, long end);

    /**
     * 删除 ZSet 中指定分数范围的成员
     *
     * @param key   键
     * @param min   最小分值
     * @param max   最大分值
     * @return 删除的数量
     */
    Long zRemRangeByScore(String key, double min, double max);

    /**
     * 获取 ZSet 中成员的数量
     *
     * @param key 键
     * @return 成员数量
     */
    Long zCard(String key);

    /**
     * 获取 ZSet 中指定成员的 score
     *
     * @param key   键
     * @param value 成员值
     * @return 分值
     */
    <T> Double zScore(String key, T value);

    /**
     * 增加 ZSet 中某个成员的 score
     *
     * @param key   键
     * @param value 成员值
     * @param delta 增量
     * @return 更新后的 score
     */
    <T> Double zIncrementScore(String key, T value, double delta);

    /**
     * 获得缓存的基本对象列表
     *
     * @param pattern 字符串前缀
     * @return 对象列表
     */
    Collection<String> keys(final String pattern);

    /**
     * 更新LIST缓存字典
     *
     * @param optionKey 集合缓存键值
     * @param dataList  数据集合
     */
    <T> void refreshListCache(String optionKey, List<T> dataList);

    /**
     * 删除MAP缓存字典指定键数据
     *
     * @param mapKey    缓存键值
     * @param cacheList 缓存数据集合
     * @param keyGet    键名
     */
    <T> void refreshMapCache(String mapKey, Collection<T> cacheList, Function<? super T, String> keyGet);

    /**
     * 更新MAP缓存字典
     *
     * @param mapKey    缓存键值
     * @param cacheList 缓存数据集合
     * @param keyGet    键名
     * @param valueGet  值名
     */
    <T, K> void refreshMapCache(String mapKey, Collection<T> cacheList, Function<? super T, String> keyGet, Function<? super T, K> valueGet);
}
