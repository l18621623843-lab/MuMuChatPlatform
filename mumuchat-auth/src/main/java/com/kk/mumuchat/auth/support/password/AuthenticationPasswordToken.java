package com.kk.mumuchat.auth.support.password;

import com.kk.mumuchat.auth.support.base.AuthenticationBaseToken;
import com.kk.mumuchat.common.core.constant.basic.SecurityConstants;
import org.springframework.security.core.Authentication;

import java.io.Serial;
import java.util.Map;
import java.util.Set;

/**
 * 自定义授权模式 | 密码模式
 *
 * @author mumuchat
 */
public class AuthenticationPasswordToken extends AuthenticationBaseToken {

    @Serial
    private static final long serialVersionUID = 1L;

    public AuthenticationPasswordToken(Authentication clientPrincipal, Set<String> scopes, Map<String, Object> additionalParameters) {
        super(SecurityConstants.GrantType.PASSWORD, clientPrincipal, scopes, additionalParameters);
    }
}
