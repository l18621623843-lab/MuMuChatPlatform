package com.kk.mumuchat.auth.handler;

import com.kk.mumuchat.auth.service.impl.FormEventHandlerImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

/**
 * 统一认证登录 - 授权码模式
 *
 * @author mumuchat
 */
public final class FormIdentityLoginConfigurer extends AbstractHttpConfigurer<FormIdentityLoginConfigurer, HttpSecurity> {

    @Override
    public void init(HttpSecurity http) throws Exception {
        http.formLogin(formLogin -> {
                    formLogin.loginPage("/token/login");
                    formLogin.loginProcessingUrl("/oauth2/form");
                    formLogin.failureHandler(new FormEventHandlerImpl());
                })
                // SSO登出成功处理
                .logout(logout -> logout.logoutUrl("/oauth2/logout")
                        .logoutSuccessHandler(new FormEventHandlerImpl())
                        .deleteCookies("JSESSIONID")
                        .invalidateHttpSession(true))
                .csrf(AbstractHttpConfigurer::disable);
    }
}
