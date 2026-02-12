package com.kk.mumuchat.auth.support.H5Embed;

import com.kk.mumuchat.auth.support.base.AuthenticationBaseConverter;
import com.kk.mumuchat.common.core.constant.basic.SecurityConstants;
import com.kk.mumuchat.common.core.utils.core.StrUtil;
import org.springframework.security.core.Authentication;

import java.util.Map;
import java.util.Set;

/**
 * 自定义验证转换器 | 微信小程序模式
 *
 * @author xueyi
 */
public class AuthenticationH5EmbedConverter extends AuthenticationBaseConverter<AuthenticationH5EmbedToken> {

    /**
     * 判断授权类型
     *
     * @param grantType 授权类型
     * @return 结果
     */
    @Override
    public boolean support(String grantType) {
        return StrUtil.equals(SecurityConstants.GrantType.H5_EMBED.getCode(), grantType);
    }

    /**
     * 构建token
     *
     * @param clientPrincipal      身份验证对象
     * @param requestedScopes      请求范围
     * @param additionalParameters 扩展信息
     * @return Token
     */
    @Override
    public AuthenticationH5EmbedToken buildToken(Authentication clientPrincipal, Set<String> requestedScopes, Map<String, Object> additionalParameters) {
        return new AuthenticationH5EmbedToken(clientPrincipal, requestedScopes, additionalParameters);
    }
}
