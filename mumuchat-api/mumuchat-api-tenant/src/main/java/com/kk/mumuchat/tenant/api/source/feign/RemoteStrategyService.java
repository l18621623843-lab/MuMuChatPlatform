package com.kk.mumuchat.tenant.api.source.feign;

import com.kk.mumuchat.common.core.web.feign.RemoteCacheService;
import com.kk.mumuchat.tenant.api.source.feign.factory.RemoteStrategyFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 租户服务 | 策略模块 | 源策略服务
 *
 * @author mumuchat
 */
@FeignClient(contextId = "remoteStrategyService", path = "/inner/strategy", value = "${xueyi.remote.service.tenant}", fallbackFactory = RemoteStrategyFallbackFactory.class)
public interface RemoteStrategyService extends RemoteCacheService {
}