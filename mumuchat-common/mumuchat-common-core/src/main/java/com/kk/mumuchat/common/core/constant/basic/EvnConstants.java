package com.kk.mumuchat.common.core.constant.basic;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 环境通用常量
 *
 * @author mumuchat
 */
public class EvnConstants {

    /** 环境类型 */
    @Getter
    @AllArgsConstructor
    public enum EvnType {

        DEV("dev", "开发环境"),
        PROD("prod", "生产环境"),
        UAT("uat", "测试环境");

        private final String code;
        private final String info;

    }

    /** 环境常量 - dev环境 */
    public static final String EVN_DEV = "dev";

    /** 环境常量 - prod环境 */
    public static final String EVN_PROD = "prod";

    /** 环境常量 - uat环境 */
    public static final String EVN_UAT = "uat";

}
