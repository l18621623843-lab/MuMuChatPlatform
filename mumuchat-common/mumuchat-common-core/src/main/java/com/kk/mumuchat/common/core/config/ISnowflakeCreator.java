package com.kk.mumuchat.common.core.config;

import cn.hutool.core.lang.Snowflake;
import com.kk.mumuchat.common.core.utils.core.IdUtil;

/**
 * Snowflake创建器
 *
 * @author mumuchat
 */
public interface ISnowflakeCreator {

    /**
     * 创建Snowflake
     *
     * @return Snowflake
     */
    default Snowflake createSnowflake() {
        return IdUtil.getSnowflake();
    }
}
