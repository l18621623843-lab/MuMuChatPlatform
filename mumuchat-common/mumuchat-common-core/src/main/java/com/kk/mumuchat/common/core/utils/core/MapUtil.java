package com.kk.mumuchat.common.core.utils.core;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Map工具类
 *
 * @author xueyi
 */
public class MapUtil extends cn.hutool.core.map.MapUtil {

    /**
     * 非空判断
     *
     * @param map Map
     * @return 结果
     */
    public static boolean isNotNull(Map<?, ?> map) {
        return !isNull(map);
    }

    /**
     * 空判断
     *
     * @param map Map
     * @return 结果
     */
    public static boolean isNull(Map<?, ?> map) {
        return null == map;
    }

    /**
     * 是否不存在指定键判断
     *
     * @param map Map
     * @return 结果
     */
    public static <T> boolean notContainsKey(Map<T, ?> map, T key) {
        return !containsKey(map, key);
    }

    /**
     * 是否存在指定键判断
     *
     * @param map Map
     * @return 结果
     */
    public static <T> boolean containsKey(Map<T, ?> map, T key) {
        return isNotEmpty(map) && map.containsKey(key);
    }

    /**
     * 构建List转Map
     *
     * @param list        list数据集合
     * @param keyMapper   key映射方法
     * @param valueMapper value映射方法
     * @return Map数据集合
     */
    public static <K, V, T> Map<K, V> buildListToMap(Collection<T> list, Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends V> valueMapper) {
        if (CollUtil.isEmpty(list)) {
            return new HashMap<>();
        }
        return list.stream().filter(ObjectUtil::isNotNull).collect(Collectors.toMap(keyMapper, valueMapper, (v1, v2) -> v1));
    }
}