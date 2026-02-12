package com.kk.mumuchat.system.api.dict.feign.factory;

import com.kk.mumuchat.common.core.web.result.R;
import com.kk.mumuchat.system.api.dict.domain.dto.SysConfigDto;
import com.kk.mumuchat.system.api.dict.feign.RemoteConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 系统服务|字典模块|参数服务 降级处理
 *
 * @author xueyi
 */
@Slf4j
@Component
public class RemoteConfigFallbackFactory implements FallbackFactory<RemoteConfigService> {

    @Override
    public RemoteConfigService create(Throwable throwable) {
        log.error("参数服务调用失败:{},失败原因：", throwable.getMessage(), throwable);
        return new RemoteConfigService() {

            @Override
            public R<Boolean> syncCacheInner() {
                return R.fail("同步参数缓存失败:" + throwable.getMessage());
            }

            @Override
            public R<Boolean> refreshCacheInner() {
                return R.fail("刷新参数缓存失败:" + throwable.getMessage());
            }

            @Override
            public R<Boolean> refreshCommonCacheInner() {
                return R.fail("刷新参数缓存失败:" + throwable.getMessage());
            }

            @Override
            public R<SysConfigDto> getInfoInner(String code) {
                return R.fail("查询参数详情失败:" + throwable.getMessage());
            }

            @Override
            public R<Boolean> editInner(SysConfigDto config) {
                return R.fail("修改参数缓存失败:" + throwable.getMessage());
            }
        };
    }
}