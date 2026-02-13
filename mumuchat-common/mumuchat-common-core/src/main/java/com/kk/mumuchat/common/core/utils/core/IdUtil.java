package com.kk.mumuchat.common.core.utils.core;

import cn.hutool.core.lang.Snowflake;
import com.kk.mumuchat.common.core.config.ISnowflakeCreator;

/**
 * 唯一ID工具类
 *
 * @author mumuchat
 */
public class IdUtil extends cn.hutool.core.util.IdUtil {

    public static final Snowflake cusSnowflake = SpringUtil.getBean(ISnowflakeCreator.class).createSnowflake();

    public static long getSnowflakeNextId() {
        return cusSnowflake.nextId();
    }

    public static String getSnowflakeNextIdStr() {
        return cusSnowflake.nextIdStr();
    }
}