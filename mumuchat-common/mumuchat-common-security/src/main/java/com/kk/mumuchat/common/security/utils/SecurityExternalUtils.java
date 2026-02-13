package com.kk.mumuchat.common.security.utils;

import com.alibaba.fastjson2.JSONObject;
import com.kk.mumuchat.common.core.exception.ServiceException;
import com.kk.mumuchat.common.core.utils.core.SecureUtil;
import com.kk.mumuchat.common.core.utils.core.StrUtil;
import com.kk.mumuchat.system.api.model.LoginExternal;

/**
 * 外系统端 - 权限获取工具类
 *
 * @author mumuchat
 */
public class SecurityExternalUtils extends SecurityUtils {

    public static void decryptParams(JSONObject params, String sign) {
        LoginExternal loginExternal = SecurityExternalUtils.getLoginUser();
        params.put("signature", loginExternal.getSignature());
        if (StrUtil.isBlank(sign)) {
            throw new ServiceException("签名不能为空");
        } else if (StrUtil.notEquals(sign, SecureUtil.signParamsMd5(params))) {
            throw new ServiceException("签名异常");
        }
    }
}
