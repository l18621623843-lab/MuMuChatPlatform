package com.kk.mumuchat.system.api.authority.feign.factory;

import com.kk.mumuchat.common.core.web.result.R;
import com.kk.mumuchat.system.api.authority.feign.RemoteExternalLoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 登录服务|外系统端 降级处理
 *
 * @author xueyi
 */
@Slf4j
@Component
public class RemoteExternalLoginFallbackFactory implements FallbackFactory<RemoteExternalLoginService> {

    @Override
    public RemoteExternalLoginService create(Throwable throwable) {
        log.error("登录服务调用失败:{}", throwable.getMessage());
        return (enterpriseName, secret) -> R.fail("获取登录信息失败:" + throwable.getMessage());
    }
}
