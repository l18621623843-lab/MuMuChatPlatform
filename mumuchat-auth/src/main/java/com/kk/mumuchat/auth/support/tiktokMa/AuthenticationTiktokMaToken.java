package com.kk.mumuchat.auth.support.tiktokMa;

import com.kk.mumuchat.auth.support.base.AuthenticationBaseToken;
import com.kk.mumuchat.common.core.constant.basic.SecurityConstants;
import org.springframework.security.core.Authentication;

import java.io.Serial;
import java.util.Map;
import java.util.Set;

/**
 * 自定义授权模式 | 微信小程序模式
 *
 * @author xueyi
 */
public class AuthenticationTiktokMaToken extends AuthenticationBaseToken {

    @Serial
    private static final long serialVersionUID = 1L;

    public AuthenticationTiktokMaToken(Authentication clientPrincipal, Set<String> scopes, Map<String, Object> additionalParameters) {
        super(SecurityConstants.GrantType.TIKTOK_MA, clientPrincipal, scopes, additionalParameters);
    }
}
