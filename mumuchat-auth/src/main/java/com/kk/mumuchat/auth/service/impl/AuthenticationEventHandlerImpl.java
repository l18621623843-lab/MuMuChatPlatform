package com.kk.mumuchat.auth.service.impl;

import com.kk.mumuchat.auth.service.ILoginLogService;
import com.kk.mumuchat.common.core.constant.basic.Constants;
import com.kk.mumuchat.common.core.constant.basic.SecurityConstants;
import com.kk.mumuchat.common.core.utils.core.SpringUtil;
import com.kk.mumuchat.common.core.utils.core.StrUtil;
import com.kk.mumuchat.common.core.utils.servlet.ServletUtil;
import com.kk.mumuchat.common.core.web.model.BaseLoginUser;
import com.kk.mumuchat.common.core.web.result.AjaxResult;
import com.kk.mumuchat.common.security.service.ITokenService;
import com.kk.mumuchat.common.security.utils.SecurityUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.Optional;

/**
 * 自定义登录操作类
 *
 * @author mumuchat
 */
@Slf4j
public class AuthenticationEventHandlerImpl implements AuthenticationSuccessHandler, AuthenticationFailureHandler {

    private final ILoginLogService logService = SpringUtil.getBean(ILoginLogService.class);

    /**
     * 登录成功
     */
    @Override
    @SneakyThrows
    @SuppressWarnings(value = {"unchecked", "rawtypes"})
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        OAuth2AccessTokenAuthenticationToken accessTokenAuthentication = (OAuth2AccessTokenAuthenticationToken) authentication;
        Map<String, Object> map = accessTokenAuthentication.getAdditionalParameters();
        BaseLoginUser loginUser = (BaseLoginUser) map.get(SecurityConstants.BaseSecurity.USER_INFO.getCode());
        Assert.notNull(loginUser, "loginUser cannot be null");
        // output login log
        logService.recordLoginInfo(loginUser, Constants.LOGIN_SUCCESS, "登录成功");
        ITokenService tokenService = SecurityUtils.getTokenService(loginUser.getAccountType().getCode());
        Assert.notNull(tokenService, "tokenService cannot be null");
        // 无状态 注意删除 context 上下文的信息
        SecurityContextHolder.clearContext();
        ServletUtil.webResponseWriter(response, AjaxResult.success(tokenService.createToken(loginUser)));
    }

    /**
     * 登录失败
     */
    @Override
    @SneakyThrows
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
        SecurityConstants.AccountType accountType = Optional.ofNullable(request.getParameter(SecurityConstants.OAuth2ParameterNames.ACCOUNT_TYPE.getCode()))
                .map(SecurityConstants.AccountType::getByCodeElseNull).orElse(null);
        SecurityConstants.GrantType grantType = Optional.ofNullable(request.getParameter(SecurityConstants.OAuth2ParameterNames.GRANT_TYPE.getCode()))
                .map(SecurityConstants.GrantType::getByCodeElseNull).orElse(null);
        StringBuilder errorMsg = new StringBuilder();
        if (accountType != null && grantType != null) {
            switch (accountType) {
                case ADMIN, MERCHANT -> {
                    switch (grantType) {
                        case PASSWORD -> {
                            String enterpriseName = request.getParameter(SecurityConstants.LoginParam.ENTERPRISE_NAME.getCode());
                            String userName = request.getParameter(SecurityConstants.LoginParam.USER_NAME.getCode());
                            errorMsg.append(StrUtil.format("企业账号:{}，用户账号:{}", enterpriseName, userName));
                        }
                        default -> {
                        }
                    }
                }
                case MEMBER, EXTERNAL -> {
                }
            }
        }
        if (!errorMsg.isEmpty()) {
            errorMsg.append(StrUtil.COMMA);
        }
        log.info("【登录失败】账户类型：{},认证模式：{},{}异常原因：{}",
                Optional.ofNullable(accountType).map(SecurityConstants.AccountType::getInfo).orElse(StrUtil.EMPTY),
                Optional.ofNullable(grantType).map(SecurityConstants.GrantType::getInfo).orElse(StrUtil.EMPTY),
                errorMsg, exception.getLocalizedMessage());
        ServletUtil.webResponseWriter(response, AjaxResult.error(exception.getLocalizedMessage()));
    }
}
