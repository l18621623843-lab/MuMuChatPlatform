package com.kk.mumuchat.system.api.authority.feign;

import com.kk.mumuchat.common.core.constant.basic.SecurityConstants;
import com.kk.mumuchat.common.core.web.result.R;
import com.kk.mumuchat.system.api.authority.feign.factory.RemoteAdminLoginFallbackFactory;
import com.kk.mumuchat.system.api.model.LoginUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 登录服务|管理端
 *
 * @author mumuchat
 */
@FeignClient(contextId = "remoteLoginService", path = "/inner/login/admin", value = "${xueyi.remote.service.system}", fallbackFactory = RemoteAdminLoginFallbackFactory.class)
public interface RemoteAdminLoginService {

    /**
     * 查询登录信息
     *
     * @param enterpriseName 企业账号
     * @param userName       员工账号
     * @param password       密码
     * @return 结果
     */
    @GetMapping(headers = SecurityConstants.FROM_SOURCE_INNER)
    R<LoginUser> getLoginAdminInfoInner(@RequestParam("enterpriseName") String enterpriseName, @RequestParam("userName") String userName, @RequestParam("password") String password);

}
