package com.kk.mumuchat.common.core.utils.core;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 对象工具类
 *
 * @author mumuchat
 */
public class ObjectUtil extends cn.hutool.core.util.ObjectUtil {

    public static boolean isAllNotNull(Object... objs) {
        return !hasNull(objs);
    }

    /**
     * 当值为空时，进行指定操作
     *
     * @param obj      对象值
     * @param runnable 操作方法
     */
    public static <T> void setObjIfNull(T obj, Runnable runnable) {
        if (isNull(obj)) {
            runnable.run();
        }
    }

    /**
     * 当值不为空时，进行指定操作
     *
     * @param obj      对象值
     * @param consumer 操作方法
     */
    public static <T> void setObjIfNotNull(T obj, Consumer<T> consumer) {
        if (isNotNull(obj)) {
            consumer.accept(obj);
        }
    }

    /**
     * 当值不为空时，进行指定操作
     *
     * @param obj      对象值
     * @param runnable 操作方法
     */
    public static <T> void setObjIfNotNull(T obj, Runnable runnable) {
        if (isNotNull(obj)) {
            runnable.run();
        }
    }

    /**
     * 当值为空时，取代替值
     *
     * @param obj     对象值
     * @param elseObj 代替值
     */
    public static <T> T getObjOrNullElse(T obj, T elseObj) {
        return isNull(obj) ? elseObj : obj;
    }

    /**
     * 当对象不为空时，使用指定函数处理该对象；当对象为空时，返回指定的替代值
     *
     * @param obj      待处理的对象
     * @param func     处理函数，当obj不为空时应用此函数
     * @param emptyObj 当obj为空时返回的替代值
     * @return 处理后的结果或替代值
     */
    public static <T, R> R getObjByFuncOrNullElse(T obj, Function<T, R> func, R emptyObj) {
        if (isNull(obj)) {
            return emptyObj;
        }
        return func.apply(obj);
    }
}