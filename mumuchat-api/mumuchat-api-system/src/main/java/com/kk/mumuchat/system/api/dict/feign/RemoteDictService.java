package com.kk.mumuchat.system.api.dict.feign;

import com.kk.mumuchat.common.core.constant.basic.SecurityConstants;
import com.kk.mumuchat.common.core.web.feign.RemoteCacheService;
import com.kk.mumuchat.common.core.web.result.R;
import com.kk.mumuchat.system.api.dict.feign.factory.RemoteDictFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 系统服务|字典模块|字典服务
 *
 * @author xueyi
 */
@FeignClient(contextId = "remoteDictService", path = "/inner/dict/type", value = "${xueyi.remote.service.system}", fallbackFactory = RemoteDictFallbackFactory.class)
public interface RemoteDictService extends RemoteCacheService {

    /**
     * 同步字典缓存|租户数据
     *
     * @return 结果
     */
    @GetMapping(value = "/sync", headers = SecurityConstants.FROM_SOURCE_INNER)
    R<Boolean> syncCacheInner();

    /**
     * 刷新字典缓存|默认数据
     *
     * @return 结果
     */
    @GetMapping(value = "/common/refresh", headers = SecurityConstants.FROM_SOURCE_INNER)
    R<Boolean> refreshCommonCacheInner();

}