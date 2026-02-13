package com.kk.mumuchat.auth.support.wechatMa;

import com.kk.mumuchat.auth.support.base.AuthenticationBaseToken;
import com.kk.mumuchat.common.core.constant.basic.SecurityConstants;
import org.springframework.security.core.Authentication;

import java.io.Serial;
import java.util.Map;
import java.util.Set;

/**
 * 自定义授权模式 | 微信小程序模式
 *
 * @author mumuchat
 */
public class AuthenticationWechatMaToken extends AuthenticationBaseToken {

    @Serial
    private static final long serialVersionUID = 1L;

    public AuthenticationWechatMaToken(Authentication clientPrincipal, Set<String> scopes, Map<String, Object> additionalParameters) {
        super(SecurityConstants.GrantType.WECHAT_MA, clientPrincipal, scopes, additionalParameters);
    }
}
