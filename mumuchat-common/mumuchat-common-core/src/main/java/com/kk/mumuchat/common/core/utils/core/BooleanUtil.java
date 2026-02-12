package com.kk.mumuchat.common.core.utils.core;

/**
 * Boolean类型工具类
 *
 * @author xueyi
 */
public class BooleanUtil extends cn.hutool.core.util.BooleanUtil {

    /**
     * 判断多个布尔值中是否全部为 true
     *
     * @param values 可变参数，多个布尔值
     * @return 全部 true 返回 true，否则返回 false
     */
    public static Boolean isAllTrue(Boolean... values) {
        if (ArrayUtil.isEmpty(values)) {
            return false;
        }
        for (boolean value : values) {
            if (isFalse(value)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断多个布尔值中是否包含 true
     *
     * @param values 可变参数，多个布尔值
     * @return 存在 true 返回 true，否则返回 false
     */
    public static Boolean hasAnyTrue(Boolean... values) {
        if (ArrayUtil.isEmpty(values)) {
            return false;
        }
        for (boolean value : values) {
            if (isTrue(value)) {
                return true;
            }
        }
        return false;
    }
}