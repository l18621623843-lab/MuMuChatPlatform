package com.kk.mumuchat.system.api.authority.feign;

import com.kk.mumuchat.common.core.constant.basic.SecurityConstants;
import com.kk.mumuchat.common.core.web.result.R;
import com.kk.mumuchat.system.api.authority.feign.factory.RemoteExternalLoginFallbackFactory;
import com.kk.mumuchat.system.api.model.LoginExternal;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 登录服务|外系统端
 *
 * @author mumuchat
 */
@FeignClient(contextId = "remoteExternalLoginService", path = "/inner/login/external", value = "${xueyi.remote.service.system}", fallbackFactory = RemoteExternalLoginFallbackFactory.class)
public interface RemoteExternalLoginService {

    /**
     * 查询登录登录信息
     *
     * @param enterpriseName 企业账号
     * @param secret 令牌密钥
     * @return 结果
     */
    @GetMapping(headers = SecurityConstants.FROM_SOURCE_INNER)
    R<LoginExternal> getLoginInfoInner(@RequestParam("enterpriseName") String enterpriseName, @RequestParam("secret") String secret);
}
