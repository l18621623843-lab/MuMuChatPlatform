package com.kk.mumuchat.auth.login.admin;

import com.kk.mumuchat.auth.login.base.IUserDetailsService;
import com.kk.mumuchat.auth.service.ILoginLogService;
import com.kk.mumuchat.common.core.constant.basic.BaseConstants;
import com.kk.mumuchat.common.core.constant.basic.Constants;
import com.kk.mumuchat.common.core.constant.basic.SecurityConstants;
import com.kk.mumuchat.common.core.utils.core.ConvertUtil;
import com.kk.mumuchat.common.core.utils.core.MapUtil;
import com.kk.mumuchat.common.core.utils.core.ObjectUtil;
import com.kk.mumuchat.common.core.utils.core.SpringUtil;
import com.kk.mumuchat.common.core.utils.core.StrUtil;
import com.kk.mumuchat.common.core.utils.servlet.ServletUtil;
import com.kk.mumuchat.common.core.web.result.R;
import com.kk.mumuchat.system.api.authority.feign.RemoteAdminLoginService;
import com.kk.mumuchat.system.api.model.LoginUser;
import com.kk.mumuchat.system.api.organize.constant.OrganizeConstants;
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
 * @author mumuchat
 */
@Component
public class AdminDetailsPasswordProvider implements IUserDetailsService {

    @Resource
    private ILoginLogService logService;

    @Resource
    private RemoteAdminLoginService remoteLoginService;

    /**
     * 校验授权类型与账户类型
     *
     * @param grantType   授权类型
     * @param accountType 账户类型
     * @return 结果
     */
    @Override
    public boolean support(String grantType, String accountType) {
        return StrUtil.equals(SecurityConstants.GrantType.PASSWORD.getCode(), grantType) && StrUtil.equals(SecurityConstants.AccountType.ADMIN.getCode(), accountType);
    }

    /**
     * 参数校验
     *
     * @param request 请求体
     */
    @Override
    @SneakyThrows
    public void checkParams(HttpServletRequest request) {
        MultiValueMap<String, String> parameters = ServletUtil.getParameters(request);

        // 企业账号校验
        String enterpriseName = parameters.getFirst(SecurityConstants.LoginParam.ENTERPRISE_NAME.getCode());
        String userName = parameters.getFirst(SecurityConstants.LoginParam.USER_NAME.getCode());
        String password = parameters.getFirst(SecurityConstants.LoginParam.PASSWORD.getCode());

        // 企业账号||员工账号||密码为空 错误
        if (StrUtil.isBlank(enterpriseName) || StrUtil.isBlank(userName) || StrUtil.isBlank(password)) {
            SpringUtil.getBean(ILoginLogService.class).recordLoginInfo(enterpriseName, userName, Constants.LOGIN_FAIL, "企业账号/员工账号/密码必须填写");
            throw new OAuth2AuthenticationException(new OAuth2Error(OAuth2ErrorCodes.INVALID_REQUEST, "企业账号/员工账号/密码必须填写", null));
        }

        // 企业账号不在指定范围内 错误
        if (enterpriseName.length() < OrganizeConstants.ENTERPRISE_NAME_MIN_LENGTH
                || enterpriseName.length() > OrganizeConstants.ENTERPRISE_NAME_MAX_LENGTH) {
            SpringUtil.getBean(ILoginLogService.class).recordLoginInfo(enterpriseName, userName, Constants.LOGIN_FAIL, "企业账号不在指定范围");
            throw new OAuth2AuthenticationException(new OAuth2Error(OAuth2ErrorCodes.INVALID_REQUEST, "企业账号不在指定范围", null));
        }

        // 员工账号不在指定范围内 错误
        if (userName.length() < OrganizeConstants.USERNAME_MIN_LENGTH
                || userName.length() > OrganizeConstants.USERNAME_MAX_LENGTH) {
            SpringUtil.getBean(ILoginLogService.class).recordLoginInfo(enterpriseName, userName, Constants.LOGIN_FAIL, "员工账号不在指定范围");
            throw new OAuth2AuthenticationException(new OAuth2Error(OAuth2ErrorCodes.INVALID_REQUEST, "员工账号不在指定范围", null));
        }

        // 密码如果不在指定范围内 错误
        if (password.length() < OrganizeConstants.PASSWORD_MIN_LENGTH
                || password.length() > OrganizeConstants.PASSWORD_MAX_LENGTH) {
            SpringUtil.getBean(ILoginLogService.class).recordLoginInfo(enterpriseName, userName, Constants.LOGIN_FAIL, "用户密码不在指定范围");
            throw new OAuth2AuthenticationException(new OAuth2Error(OAuth2ErrorCodes.INVALID_REQUEST, "用户密码不在指定范围", null));
        }
    }

    /**
     * 登录信息构建
     *
     * @param reqParameters 请求参数
     * @return 用户信息
     */
    public UsernamePasswordAuthenticationToken buildToken(Map<String, Object> reqParameters) {
        String enterpriseName = (String) reqParameters.get(SecurityConstants.LoginParam.ENTERPRISE_NAME.getCode());
        String userName = (String) reqParameters.get(SecurityConstants.LoginParam.USER_NAME.getCode());
        String password = (String) reqParameters.get(SecurityConstants.LoginParam.PASSWORD.getCode());
        Map<String, String> loginMap = new HashMap<>();
        loginMap.put(SecurityConstants.BaseSecurity.ENTERPRISE_NAME.getCode(), enterpriseName);
        loginMap.put(SecurityConstants.BaseSecurity.USER_NAME.getCode(), userName);
        loginMap.put(SecurityConstants.BaseSecurity.PASSWORD.getCode(), password);
        return new UsernamePasswordAuthenticationToken(loginMap, password);
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
        String enterpriseName = loginMap.get(SecurityConstants.BaseSecurity.ENTERPRISE_NAME.getCode());
        String userName = loginMap.get(SecurityConstants.BaseSecurity.USER_NAME.getCode());
        String password = loginMap.get(SecurityConstants.BaseSecurity.PASSWORD.getCode());
        return loadUser(enterpriseName, userName, password);
    }

    /**
     * 登录验证
     *
     * @param enterpriseName 企业名称
     * @param userName       用户名
     * @param password       密码
     * @return 用户信息
     */
    @SneakyThrows
    public LoginUser loadUser(String enterpriseName, String userName, String password) {
        // 查询登录信息
        R<LoginUser> loginInfoResult = remoteLoginService.getLoginAdminInfoInner(enterpriseName, userName, password);
        if (loginInfoResult.isFail()) {
            throw new UsernameNotFoundException(loginInfoResult.getMsg());
        } else if (ObjectUtil.isNull(loginInfoResult.getData())) {
            logService.recordLoginInfo(enterpriseName, userName, Constants.LOGIN_FAIL, loginInfoResult.getMsg());
            throw new UsernameNotFoundException("企业账号/员工账号/密码错误，请检查！");
        }
        LoginUser loginUser = loginInfoResult.getData();
        if (BaseConstants.Status.DISABLE.getCode().equals(loginUser.getUser().getStatus())) {
            logService.recordLoginInfo(loginUser, Constants.LOGIN_FAIL, "用户已停用，请联系管理员");
            throw new UsernameNotFoundException("对不起，您的账号：" + userName + " 已停用！");
        }
        loginUser.setPassword(SecurityConstants.BCRYPT + loginUser.getPassword());
        return loginUser;
    }

    @Override
    public int getOrder() {
        return Integer.MIN_VALUE;
    }
}
