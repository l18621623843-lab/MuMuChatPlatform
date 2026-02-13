package com.kk.mumuchat.system.api.dict.feign.factory;

import com.kk.mumuchat.common.core.web.result.R;
import com.kk.mumuchat.system.api.dict.feign.RemoteDictService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 系统服务|字典模块|字典服务 降级处理
 *
 * @author mumuchat
 */
@Slf4j
@Component
public class RemoteDictFallbackFactory implements FallbackFactory<RemoteDictService> {

    @Override
    public RemoteDictService create(Throwable throwable) {
        log.error("字典服务调用失败:{},失败原因：", throwable.getMessage(), throwable);
        return new RemoteDictService() {

            @Override
            public R<Boolean> syncCacheInner() {
                return R.fail("同步字典缓存失败:" + throwable.getMessage());
            }

            @Override
            public R<Boolean> refreshCacheInner() {
                return R.fail("刷新字典缓存失败:" + throwable.getMessage());
            }

            @Override
            public R<Boolean> refreshCommonCacheInner() {
                return R.fail("刷新字典缓存失败:" + throwable.getMessage());
            }
        };
    }
}