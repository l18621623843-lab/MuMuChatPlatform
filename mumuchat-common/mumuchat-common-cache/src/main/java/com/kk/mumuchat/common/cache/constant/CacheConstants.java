package com.kk.mumuchat.common.cache.constant;

import com.kk.mumuchat.common.core.constant.basic.DictConstants;
import com.kk.mumuchat.common.core.utils.core.EnumUtil;
import com.kk.mumuchat.common.core.utils.core.StrUtil;
import com.kk.mumuchat.common.core.web.entity.cache.ICacheService;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 缓存的key通用常量
 *
 * @author mumuchat
 */
public class CacheConstants {

    /** 缓存有效期，默认720（分钟） */
    public final static long EXPIRATION = 720;

    /** 缓存有效期，默认360（分钟） */
    public final static long ACCESS_TIME = 360;

    /** 缓存刷新时间，默认120（分钟） */
    public final static long REFRESH_TIME = 120;

    public final static String AUTHORIZATION = "token";

    /** oauth 客户端信息 */
    public final static String CLIENT_DETAILS_KEY = "client:details";

    /** 参数类型 */
    @Getter
    @AllArgsConstructor
    public enum ConfigType {

        USER_INIT_PASSWORD("sys.user.initPassword", "用户管理-账号初始密码", String.class, StrUtil.EMPTY),
        SYS_CODE_SHOW("sys.code.show", "系统模块:数据编码配置:功能开关（Y启用 N禁用）", String.class, DictConstants.DicYesNo.NO.getCode()),
        SYS_CODE_MUST("sys.code.must", "系统模块:数据编码配置:必须字段（Y是 N否）", String.class, DictConstants.DicYesNo.NO.getCode());

        private final String code;
        private final String info;
        private final Class<?> clazz;
        private final Object defaultValue;

        public static ConfigType getByCodeElseNull(String code) {
            return EnumUtil.getByCodeElseNull(ConfigType.class, code);
        }
    }

    /** 缓存类型 */
    @Getter
    @AllArgsConstructor
    public enum CacheKey implements ICacheService {

        CACHE_REPEAT_KEY("system:cacheRepeat:{}:{}", "缓存重复访问");

        private final String code;
        private final String info;

    }

    /** 登录缓存类型 */
    @Getter
    @AllArgsConstructor
    public enum LoginTokenType {

        ADMIN("login_tokens", "管理端"),
        MEMBER("login_member_tokens", "会员端"),
        PLATFORM("login_platform_tokens", "平台端"),
        MERCHANT("login_merchant_tokens", "商户端"),
        EXTERNAL("login_external_tokens", "第三方外系统");

        private final String code;
        private final String info;

    }
}
