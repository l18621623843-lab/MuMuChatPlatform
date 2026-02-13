package com.kk.mumuchat.auth.support.H5Embed;

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
public class AuthenticationH5EmbedToken extends AuthenticationBaseToken {

    @Serial
    private static final long serialVersionUID = 1L;

    public AuthenticationH5EmbedToken(Authentication clientPrincipal, Set<String> scopes, Map<String, Object> additionalParameters) {
        super(SecurityConstants.GrantType.H5_EMBED, clientPrincipal, scopes, additionalParameters);
    }
}
