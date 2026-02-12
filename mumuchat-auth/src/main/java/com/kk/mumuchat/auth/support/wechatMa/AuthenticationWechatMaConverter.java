package com.kk.mumuchat.auth.support.wechatMa;

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
public class AuthenticationWechatMaConverter extends AuthenticationBaseConverter<AuthenticationWechatMaToken> {

    /**
     * 判断授权类型
     *
     * @param grantType 授权类型
     * @return 结果
     */
    @Override
    public boolean support(String grantType) {
        return StrUtil.equals(SecurityConstants.GrantType.WECHAT_MA.getCode(), grantType);
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
    public AuthenticationWechatMaToken buildToken(Authentication clientPrincipal, Set<String> requestedScopes, Map<String, Object> additionalParameters) {
        return new AuthenticationWechatMaToken(clientPrincipal, requestedScopes, additionalParameters);
    }
}
