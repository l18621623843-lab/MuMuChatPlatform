package com.kk.mumuchat.system.api.dict.feign;

import com.kk.mumuchat.common.core.constant.basic.SecurityConstants;
import com.kk.mumuchat.common.core.web.feign.RemoteCacheService;
import com.kk.mumuchat.common.core.web.result.R;
import com.kk.mumuchat.system.api.dict.feign.factory.RemoteImExFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 系统服务|字典模块|导入导出配置服务
 *
 * @author xueyi
 */
@FeignClient(contextId = "remoteImExService", path = "/inner/imExConfig", value = "${xueyi.remote.service.system}", fallbackFactory = RemoteImExFallbackFactory.class)
public interface RemoteImExService extends RemoteCacheService {

    /**
     * 同步导入导出配置缓存|租户数据
     *
     * @return 结果
     */
    @GetMapping(value = "/sync", headers = SecurityConstants.FROM_SOURCE_INNER)
    R<Boolean> syncCacheInner();

    /**
     * 刷新导入导出配置缓存|默认数据
     *
     * @return 结果
     */
    @GetMapping(value = "/common/refresh", headers = SecurityConstants.FROM_SOURCE_INNER)
    R<Boolean> refreshCommonCacheInner();
}