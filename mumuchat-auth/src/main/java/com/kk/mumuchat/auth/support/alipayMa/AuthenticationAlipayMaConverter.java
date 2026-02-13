package com.kk.mumuchat.auth.support.alipayMa;

import com.kk.mumuchat.auth.support.base.AuthenticationBaseConverter;
import com.kk.mumuchat.common.core.constant.basic.SecurityConstants.GrantType;
import com.kk.mumuchat.common.core.utils.core.StrUtil;
import org.springframework.security.core.Authentication;

import java.util.Map;
import java.util.Set;

/**
 * 自定义验证转换器 | 支付宝小程序模式
 *
 * @author mumuchat
 */
public class AuthenticationAlipayMaConverter extends AuthenticationBaseConverter<AuthenticationAlipayMaToken> {

    /**
     * 判断授权类型
     *
     * @param grantType 授权类型
     * @return 结果
     */
    @Override
    public boolean support(String grantType) {
        return StrUtil.equals(GrantType.ALIPAY_MA.getCode(), grantType);
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
    public AuthenticationAlipayMaToken buildToken(Authentication clientPrincipal, Set<String> requestedScopes, Map<String, Object> additionalParameters) {
        return new AuthenticationAlipayMaToken(clientPrincipal, requestedScopes, additionalParameters);
    }
}
