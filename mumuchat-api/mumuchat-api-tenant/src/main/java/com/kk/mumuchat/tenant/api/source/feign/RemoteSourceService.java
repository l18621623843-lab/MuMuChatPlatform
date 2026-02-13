package com.kk.mumuchat.tenant.api.source.feign;

import com.kk.mumuchat.common.core.web.feign.RemoteCacheService;
import com.kk.mumuchat.tenant.api.source.feign.factory.RemoteSourceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 租户服务 | 策略模块 | 数据源服务
 *
 * @author mumuchat
 */
@FeignClient(contextId = "remoteSourceService", path = "/inner/source", value = "${xueyi.remote.service.tenant}", fallbackFactory = RemoteSourceFallbackFactory.class)
public interface RemoteSourceService extends RemoteCacheService {
}