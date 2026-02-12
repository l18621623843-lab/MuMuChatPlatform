package com.kk.mumuchat.system.api.authority.feign.factory;

import com.kk.mumuchat.common.core.web.result.R;
import com.kk.mumuchat.system.api.authority.feign.RemoteAdminLoginService;
import com.kk.mumuchat.system.api.model.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 登录服务|管理端 降级处理
 *
 * @author xueyi
 */
@Slf4j
@Component
public class RemoteAdminLoginFallbackFactory implements FallbackFactory<RemoteAdminLoginService> {

    @Override
    public RemoteAdminLoginService create(Throwable throwable) {
        log.error("登录服务调用失败:{},失败原因：", throwable.getMessage(), throwable);
        return new RemoteAdminLoginService() {
            @Override
            public R<LoginUser> getLoginAdminInfoInner(String enterpriseName, String userName, String password) {
                return R.fail("获取登录信息失败:" + throwable.getMessage());
            }
        };
    }
}
