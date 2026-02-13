package com.kk.mumuchat.system.api.authority.feign.factory;

import com.kk.mumuchat.common.core.web.result.R;
import com.kk.mumuchat.system.api.authority.feign.RemoteClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 系统服务|权限模块|客户端认证服务 降级处理
 *
 * @author mumuchat
 */
@Slf4j
@Component
public class RemoteClientFallbackFactory implements FallbackFactory<RemoteClientService> {

    @Override
    public RemoteClientService create(Throwable throwable) {
        log.error("客户端认证服务调用失败:", throwable);
        return clientId -> R.fail("获取客户端信息失败:" + throwable.getMessage());
    }
}