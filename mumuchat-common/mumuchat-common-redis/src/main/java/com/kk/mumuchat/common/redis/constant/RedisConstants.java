package com.kk.mumuchat.common.redis.constant;

import com.kk.mumuchat.common.core.utils.core.EnumUtil;
import com.kk.mumuchat.common.core.web.entity.cache.ICacheService;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 缓存 通用常量
 *
 * @author xueyi
 */
public class RedisConstants {

    /** 缓存键 */
    @Getter
    @AllArgsConstructor
    public enum CacheKey implements ICacheService {

        CLIENT_DETAILS_KEY("client:details", "oauth 客户端信息"),
        CAPTCHA_CODE_KEY("captcha_codes:", "验证码"),
        SYS_CORRELATE_KEY("system:correlate:{}.{}", "数据关联工具"),
        SYS_CORRELATE_IMPL_KEY("system:correlate:service:{}", "数据关联工具"),
        SNOWFLAKE_CREATOR_KEY("core:snowflake:creatorId", "核心|雪花生成|标识排序值");

        private final String code;
        private final String info;

    }

    /** 缓存类型 */
    @Getter
    @AllArgsConstructor
    public enum OperateType {

        REFRESH_ALL("refresh_all", "更新全部"),
        REFRESH("refresh", "更新"),
        REMOVE("remove", "删除");

        private final String code;
        private final String info;

        public static OperateType getByCode(String code) {
            return EnumUtil.getByCode(OperateType.class, code);
        }

    }
}
