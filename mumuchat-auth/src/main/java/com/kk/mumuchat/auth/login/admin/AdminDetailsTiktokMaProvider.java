package com.kk.mumuchat.auth.login.admin;

import com.kk.mumuchat.auth.login.base.IUserDetailsService;
import com.kk.mumuchat.common.core.constant.basic.BaseConstants;
import com.kk.mumuchat.common.core.constant.basic.SecurityConstants;
import com.kk.mumuchat.common.core.utils.core.ObjectUtil;
import com.kk.mumuchat.common.core.utils.core.StrUtil;
import com.kk.mumuchat.common.core.web.result.R;
import com.kk.mumuchat.system.api.model.LoginUser;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 身份验证处理器 | 抖音小程序模式 | 后台账户
 *
 * @author mumuchat
 */
@Slf4j
@Component
public class AdminDetailsTiktokMaProvider implements IUserDetailsService {

    /**
     * 校验授权类型与账户类型
     *
     * @param grantType   授权类型
     * @param accountType 账户类型
     * @return 结果
     */
    @Override
    public boolean support(String grantType, String accountType) {
        return StrUtil.equals(SecurityConstants.GrantType.TIKTOK_MA.getCode(), grantType) && StrUtil.equals(SecurityConstants.AccountType.ADMIN.getCode(), accountType);
    }

    /**
     * 参数校验
     *
     * @param request 请求体
     */
    @Override
    public void checkParams(HttpServletRequest request) {
        // TODO 自定义校验逻辑
    }

    /**
     * 登录信息构建
     *
     * @param reqParameters 请求参数
     * @return 用户信息
     */
    @Override
    public UsernamePasswordAuthenticationToken buildToken(Map<String, Object> reqParameters) {
        // TODO 自定义参数传递逻辑 参考com.kk.mumuchat.auth.login.admin.AdminDetailsPasswordProvider.buildToken
//        String enterpriseName = (String) reqParameters.get(SecurityConstants.LoginParam.ENTERPRISE_NAME.getCode());
//        String userName = (String) reqParameters.get(SecurityConstants.LoginParam.USER_NAME.getCode());
//        String password = (String) reqParameters.get(SecurityConstants.LoginParam.PASSWORD.getCode());
        Map<String, String> loginMap = new HashMap<>();
//        loginMap.put(SecurityConstants.BaseSecurity.ENTERPRISE_NAME.getCode(), enterpriseName);
//        loginMap.put(SecurityConstants.BaseSecurity.USER_NAME.getCode(), userName);
//        loginMap.put(SecurityConstants.BaseSecurity.PASSWORD.getCode(), password);
        return new UsernamePasswordAuthenticationToken(loginMap, null);
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
        // TODO 自定义LoginUser校验与构造逻辑 参考com.kk.mumuchat.auth.login.admin.AdminDetailsPasswordProvider.loadUser(java.lang.Object)
        return loadUser();
    }

    /**
     * 登录验证
     *
     * @return 用户信息
     */
    @SneakyThrows
    private LoginUser loadUser() {
        // TODO 自定义LoginUser校验与构造逻辑
        R<LoginUser> loginInfoResult = new R<>();
        if (ObjectUtil.isNotNull(loginInfoResult)) {

            LoginUser loginUser = loginInfoResult.getData();
            if (BaseConstants.Status.DISABLE.getCode().equals(loginUser.getUser().getStatus())) {
                throw new UsernameNotFoundException("对不起，您的账号：" + loginUser.getNickName() + " 已停用！");
            }
            return loginUser;
        }
        throw new UsernameNotFoundException("用户信息获取失败，请稍后登录！");
    }

    @Override
    public int getOrder() {
        return Integer.MIN_VALUE;
    }
}
