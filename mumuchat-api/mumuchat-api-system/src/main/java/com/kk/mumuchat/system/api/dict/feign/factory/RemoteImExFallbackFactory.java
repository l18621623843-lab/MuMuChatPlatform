package com.kk.mumuchat.system.api.dict.feign.factory;

import com.kk.mumuchat.common.core.web.result.R;
import com.kk.mumuchat.system.api.dict.feign.RemoteImExService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 系统服务|字典模块|导入导出配置服务 降级处理
 *
 * @author mumuchat
 */
@Slf4j
@Component
public class RemoteImExFallbackFactory implements FallbackFactory<RemoteImExService> {

    @Override
    public RemoteImExService create(Throwable throwable) {
        log.error("导入导出配置服务调用失败:{},失败原因：", throwable.getMessage(), throwable);
        return new RemoteImExService() {

            @Override
            public R<Boolean> syncCacheInner() {
                return R.fail("同步导入导出配置缓存失败:" + throwable.getMessage());
            }

            @Override
            public R<Boolean> refreshCacheInner() {
                return R.fail("刷新导入导出配置缓存失败:" + throwable.getMessage());
            }

            @Override
            public R<Boolean> refreshCommonCacheInner() {
                return R.fail("刷新导入导出配置缓存失败:" + throwable.getMessage());
            }
        };
    }
}