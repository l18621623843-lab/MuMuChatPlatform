package com.kk.mumuchat.auth.service.impl;

import com.kk.mumuchat.auth.form.RegisterBody;
import com.kk.mumuchat.auth.service.ILoginService;
import com.kk.mumuchat.common.core.web.result.AjaxResult;
import com.kk.mumuchat.common.core.web.result.R;
import com.kk.mumuchat.tenant.api.tenant.feign.RemoteTenantService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 * 登录校验 服务层处理
 *
 * @author mumuchat
 */
@Component
public class LoginServiceImpl implements ILoginService {

    @Resource
    private RemoteTenantService remoteTenantService;

    /**
     * 注册
     */
    @Override
    public void register(RegisterBody registerBody) {
        // 注册租户信息
        R<?> registerResult = remoteTenantService.registerTenantInfo(registerBody.buildJson());
        if (R.FAIL == registerResult.getCode()) {
            AjaxResult.warn(registerResult.getMsg());
        }
        // 注册逻辑补充完整后再增加日志
//        logService.recordLoginInfo(TenantConstants.Source.SLAVE.getCode(), SecurityConstants.EMPTY_TENANT_ID, registerBody.getTenant().getName(), SecurityConstants.EMPTY_USER_ID, registerBody.getUser().getUserName(), Constants.REGISTER, "注册成功");
    }
}