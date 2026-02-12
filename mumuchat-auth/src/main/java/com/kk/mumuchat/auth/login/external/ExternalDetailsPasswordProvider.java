package com.kk.mumuchat.auth.login.external;

import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import com.kk.mumuchat.auth.login.base.IUserDetailsService;
import com.kk.mumuchat.common.core.constant.basic.SecurityConstants;
import com.kk.mumuchat.common.core.properties.SecretProperties;
import com.kk.mumuchat.common.core.utils.core.CharsetUtil;
import com.kk.mumuchat.common.core.utils.core.ConvertUtil;
import com.kk.mumuchat.common.core.utils.core.MapUtil;
import com.kk.mumuchat.common.core.utils.core.ObjectUtil;
import com.kk.mumuchat.common.core.utils.core.StrUtil;
import com.kk.mumuchat.common.core.utils.servlet.ServletUtil;
import com.kk.mumuchat.common.core.web.result.R;
import com.kk.mumuchat.common.security.utils.SecurityUserUtils;
import com.kk.mumuchat.system.api.authority.feign.RemoteExternalLoginService;
import com.kk.mumuchat.system.api.model.LoginExternal;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.Map;

/**
 * 身份验证处理器 | 密码模式 | 后台账户
 *
 * @author xueyi
 */
@Component
public class ExternalDetailsPasswordProvider implements IUserDetailsService {

    @Resource
    private RemoteExternalLoginService remoteLoginService;

    /**
     * 校验授权类型与账户类型
     *
     * @param grantType   授权类型
     * @param accountType 账户类型
     * @return 结果
     */
    @Override
    public boolean support(String grantType, String accountType) {
        return StrUtil.equals(SecurityConstants.GrantType.PASSWORD.getCode(), grantType) && StrUtil.equals(SecurityConstants.AccountType.EXTERNAL.getCode(), accountType);
    }

    /**
     * 参数校验
     *
     * @param request 请求体
     */
    @Override
    public void checkParams(HttpServletRequest request) {
        MultiValueMap<String, String> parameters = ServletUtil.getParameters(request);
        // 企业账号校验
        String enterpriseName = parameters.getFirst(SecurityConstants.LoginExternalParam.SIGNATURE.getCode());
        String secret = parameters.getFirst(SecurityConstants.LoginExternalParam.SECRET.getCode());
        // 企业账号为空 错误
        if (StrUtil.hasBlank(enterpriseName, secret)) {
            throw new OAuth2AuthenticationException(new OAuth2Error(OAuth2ErrorCodes.INVALID_REQUEST, "企业签名必须填写", null));
        }
    }

    /**
     * 登录信息构建
     *
     * @param reqParameters 请求参数
     * @return 用户信息
     */
    public UsernamePasswordAuthenticationToken buildToken(Map<String, Object> reqParameters) {
        String enterpriseName = (String) reqParameters.get(SecurityConstants.LoginExternalParam.SIGNATURE.getCode());
        String secret = (String) reqParameters.get(SecurityConstants.LoginExternalParam.SECRET.getCode());
        Map<String, String> loginMap = new HashMap<>();
        loginMap.put(SecurityConstants.LoginExternalParam.SIGNATURE.getCode(), enterpriseName);
        loginMap.put(SecurityConstants.LoginExternalParam.SECRET.getCode(), secret);
        return new UsernamePasswordAuthenticationToken(loginMap, enterpriseName);
    }

    /**
     * 登录验证
     *
     * @param principal 登录信息
     * @return 用户信息
     */
    @Override
    @SneakyThrows
    public UserDetails loadUser(Object principal) {
        Map<String, String> loginMap = ConvertUtil.toMap(String.class, String.class, principal);
        if (MapUtil.isEmpty(loginMap)) {
            loginMap = new HashMap<>();
        }
        String encryptStr = loginMap.get(SecurityConstants.LoginExternalParam.SIGNATURE.getCode());
        String secret = loginMap.get(SecurityConstants.LoginExternalParam.SECRET.getCode());
        String enterpriseName = null;
        if (StrUtil.isNotBlank(encryptStr)) {
            try {
                byte[] key = SecretProperties.getPlatform().getBytes(CharsetUtil.CHARSET_UTF_8);
                SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, key);
                enterpriseName = aes.decryptStr(encryptStr);
            } catch (Exception ignored) {
            }
        }
        return loadUser(encryptStr, enterpriseName, secret);
    }

    /**
     * 登录验证
     *
     * @param encryptStr     企业账号加密文本
     * @param enterpriseName 企业账号
     * @param secret         令牌密钥
     * @return 用户信息
     */
    @SneakyThrows
    public LoginExternal loadUser(String encryptStr, String enterpriseName, String secret) {
        // 查询登录信息
        R<LoginExternal> loginInfoResult = remoteLoginService.getLoginInfoInner(enterpriseName, secret);
        if (loginInfoResult.isFail()) {
            throw new UsernameNotFoundException("服务调用失败，请稍后再试！");
        } else if (ObjectUtil.isNull(loginInfoResult.getData())) {
            throw new UsernameNotFoundException("企业账号不存在，请检查！");
        }
        LoginExternal loginUser = loginInfoResult.getData();
        loginUser.setPassword(SecurityConstants.BCRYPT + SecurityUserUtils.encryptPassword(encryptStr));
        return loginUser;
    }

    @Override
    public int getOrder() {
        return Integer.MIN_VALUE;
    }
}
