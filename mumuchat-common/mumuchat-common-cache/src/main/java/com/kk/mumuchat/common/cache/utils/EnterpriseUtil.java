package com.kk.mumuchat.common.cache.utils;

import com.kk.mumuchat.common.cache.service.CacheService;
import com.kk.mumuchat.common.core.context.SecurityContextHolder;
import com.kk.mumuchat.common.core.utils.core.SpringUtil;
import com.kk.mumuchat.common.core.web.model.SysEnterprise;
import com.kk.mumuchat.tenant.api.tenant.constant.TenantConstants;

import java.util.Map;
import java.util.function.Consumer;

/**
 * 企业缓存管理工具类
 *
 * @author mumuchat
 */
public class EnterpriseUtil {

    /**
     * 获取企业缓存信息
     *
     * @param id 企业Id
     * @return 企业信息对象
     */
    public static SysEnterprise getEnterpriseCache(Long id) {
        return SpringUtil.getBean(CacheService.class).getCacheMapValue(TenantConstants.CacheType.TE_TENANT_KEY, id);
    }

    /**
     * 获取企业缓存信息Map
     *
     * @return 企业信息对象Map
     */
    public static Map<String, SysEnterprise> getEnterpriseCache() {
        return SpringUtil.getBean(CacheService.class).getCacheMap(TenantConstants.CacheType.TE_TENANT_KEY);
    }

    /**
     * 通过企业账号获取企业缓存信息
     *
     * @param enterpriseName 企业账号
     * @return 企业信息对象
     */
    public static SysEnterprise getEnterpriseCache(String enterpriseName) {
        Long enterpriseId = SpringUtil.getBean(CacheService.class).getCacheMapValue(TenantConstants.CacheType.TE_ENTERPRISE_SYSTEM_NAME_KEY, enterpriseName);
        return getEnterpriseCache(enterpriseId);
    }

    /**
     * 循环全部租户执行方法 | Consumer
     *
     * @param consumer ConsumerFun
     */
    public static void handleEnterpriseForeachFunc(Consumer<SysEnterprise> consumer) {
        EnterpriseUtil.getEnterpriseCache().forEach((id, enterpriseInfo) ->
                SecurityContextHolder.setEnterpriseIdFun(enterpriseInfo.getId(), () -> consumer.accept(enterpriseInfo)));
    }
}
