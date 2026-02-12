package com.kk.mumuchat.common.core.utils.core;

import com.kk.mumuchat.common.core.utils.core.pool.NumberPool;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * 数字工具类
 *
 * @author xueyi
 */
public class NumberUtil extends cn.hutool.core.util.NumberUtil implements NumberPool {

    public static int compare(BigDecimal x, BigDecimal y) {
        return x.compareTo(y);
    }

    /**
     * 将传入的 BigDecimal 数值转换为负数
     *
     * @param num 要转换的数值
     * @return 转换后的负数值，如果输入为 null，则返回 0
     */
    public static BigDecimal negate(BigDecimal num) {
        return Optional.ofNullable(num).map(BigDecimal::negate).orElse(BigDecimal.ZERO);
    }

    /**
     * 转换绝对值
     *
     * @param fromObj 入参数值
     * @return 绝对值数值
     */
    public static Object absoluteNumber(Object fromObj) {
        if (fromObj instanceof String str) {
            fromObj = new BigDecimal(str);
        }
        if (fromObj instanceof Integer number) {
            fromObj = number > Zero ? number : Math.abs(number);
        } else if (fromObj instanceof Double number) {
            Double zero = (double) Zero;
            fromObj = zero.compareTo(number) <= Zero ? number : Math.abs(number);
        } else if (fromObj instanceof BigDecimal number) {
            fromObj = BigDecimal.ZERO.compareTo(number) <= Zero ? number : number.abs();
        }
        return fromObj;
    }

    /**
     * 数值取反
     *
     * @param fromObj 入参数值
     * @return 绝对值数值
     */
    public static Object negateNumber(Object fromObj) {
        if (fromObj instanceof String str) {
            fromObj = new BigDecimal(str);
        }
        if (fromObj instanceof Integer number) {
            fromObj = -number;
        } else if (fromObj instanceof Double number) {
            fromObj = -number;
        } else if (fromObj instanceof BigDecimal number) {
            fromObj = number.negate();
        }
        return fromObj;
    }
}