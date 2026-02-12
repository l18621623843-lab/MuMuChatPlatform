package com.kk.mumuchat.common.core.utils.cache;

import com.kk.mumuchat.common.core.constant.basic.SecurityConstants;
import com.kk.mumuchat.common.core.context.SecurityContextHolder;
import com.kk.mumuchat.common.core.exception.ServiceException;
import com.kk.mumuchat.common.core.exception.UtilException;
import com.kk.mumuchat.common.core.utils.core.ObjectUtil;
import com.kk.mumuchat.common.core.utils.core.StrUtil;
import com.kk.mumuchat.common.core.web.entity.cache.ICusCacheService;

/**
 * 缓存工具类
 *
 * @author xueyi
 */
public class CacheUtil {

    /**
     * 获取缓存键值 | 自定义
     *
     * @param code 缓存编码
     * @return 缓存键值
     */
    public static String getCusCacheKey(String code) {
        return getCusCacheKey(code, Boolean.FALSE, null);
    }

    /**
     * 获取缓存键值 | 自定义
     * <p>
     * 根据自定义缓存服务对象获取其对应的缓存键值，适用于实现了 ICusCacheService 接口的缓存类型
     * </p>
     *
     * @param cache 自定义缓存类型
     * @return 缓存键值字符串
     */
    public static <Cache extends ICusCacheService> String getCusCacheKey(Cache cache) {
        return getCusCacheKey(cache.getCode(), cache.getIsTenant());
    }

    /**
     * 获取缓存键值 | 自定义
     *
     * @param code     缓存编码
     * @param isTenant 租户级缓存
     * @return 缓存键值
     */
    public static String getCusCacheKey(String code, Boolean isTenant) {
        Long enterpriseId = SecurityContextHolder.getEnterpriseId();
        if (isTenant && (ObjectUtil.isNull(enterpriseId) || ObjectUtil.equals(SecurityConstants.EMPTY_TENANT_ID, enterpriseId))) {
            throw new UtilException("获取字典失败，未找到企业Id标识");
        }
        return getCusCacheKey(code, isTenant, isTenant ? enterpriseId : null);
    }

    /**
     * 获取缓存键值 | 自定义
     *
     * @param code         缓存编码
     * @param isTenant     租户级缓存
     * @param enterpriseId 企业Id
     * @return 缓存键值
     */
    public static String getCusCacheKey(String code, Boolean isTenant, Long enterpriseId) {
        String cacheKey;
        if (isTenant) {
            if (ObjectUtil.isNull(enterpriseId)) {
                throw new ServiceException(StrUtil.format("缓存键{}为企业级缓存，企业Id不能为空", code));
            }
            cacheKey = StrUtil.format("{}:{}", code, enterpriseId);
        } else {
            cacheKey = code;
        }
        return cacheKey;
    }
}
