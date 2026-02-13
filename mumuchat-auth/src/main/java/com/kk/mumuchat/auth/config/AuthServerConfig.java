package com.kk.mumuchat.auth.config;

import com.kk.mumuchat.auth.handler.AuthenticationProvider;
import com.kk.mumuchat.auth.handler.CustomTokenCustomizer;
import com.kk.mumuchat.auth.handler.CustomTokenGenerator;
import com.kk.mumuchat.auth.handler.FormIdentityLoginConfigurer;
import com.kk.mumuchat.auth.service.impl.AuthenticationEventHandlerImpl;
import com.kk.mumuchat.auth.support.H5Embed.AuthenticationH5EmbedConverter;
import com.kk.mumuchat.auth.support.H5Embed.AuthenticationH5EmbedProvider;
import com.kk.mumuchat.auth.support.alipayMa.AuthenticationAlipayMaConverter;
import com.kk.mumuchat.auth.support.alipayMa.AuthenticationAlipayMaProvider;
import com.kk.mumuchat.auth.support.password.AuthenticationPasswordConverter;
import com.kk.mumuchat.auth.support.password.AuthenticationPasswordProvider;
import com.kk.mumuchat.auth.support.tiktokMa.AuthenticationTiktokMaConverter;
import com.kk.mumuchat.auth.support.tiktokMa.AuthenticationTiktokMaProvider;
import com.kk.mumuchat.auth.support.wechatMa.AuthenticationWechatMaConverter;
import com.kk.mumuchat.auth.support.wechatMa.AuthenticationWechatMaProvider;
import com.kk.mumuchat.common.core.constant.basic.SecurityConstants;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.token.DelegatingOAuth2TokenGenerator;
import org.springframework.security.oauth2.server.authorization.token.OAuth2RefreshTokenGenerator;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.security.oauth2.server.authorization.web.authentication.OAuth2AuthorizationCodeAuthenticationConverter;
import org.springframework.security.oauth2.server.authorization.web.authentication.OAuth2AuthorizationCodeRequestAuthenticationConverter;
import org.springframework.security.oauth2.server.authorization.web.authentication.OAuth2ClientCredentialsAuthenticationConverter;
import org.springframework.security.oauth2.server.authorization.web.authentication.OAuth2RefreshTokenAuthenticationConverter;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.DelegatingAuthenticationConverter;

import java.util.Arrays;

/**
 * 认证服务器配置
 *
 * @author mumuchat
 */
@Configuration
public class AuthServerConfig {

    @Resource
    private OAuth2AuthorizationService authorizationService;

    /**
     * Authorization Server 配置，仅对 /oauth2/** 的请求有效
     *
     * @param http http
     * @return {@link SecurityFilterChain }
     * @throws Exception 异常
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authorizationServer(HttpSecurity http) throws Exception {
        // 配置授权服务器的安全策略，只有/oauth2/**的请求才会走如下的配置
        http.securityMatcher("/oauth2/**");
        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer();

        http.with(authorizationServerConfigurer
                        // 个性化认证授权端点
                        .tokenEndpoint((tokenEndpoint) -> tokenEndpoint
                                // 注入自定义的授权认证Converter
                                .accessTokenRequestConverter(accessTokenRequestConverter())
                                // 登录成功处理器
                                .accessTokenResponseHandler(new AuthenticationEventHandlerImpl())
                                // 登录失败处理器
                                .errorResponseHandler(new AuthenticationEventHandlerImpl())
                        )
                        // 个性化客户端认证
                        .clientAuthentication(oAuth2ClientAuthenticationConfigurer -> oAuth2ClientAuthenticationConfigurer
                                // 处理客户端认证异常
                                .errorResponseHandler(new AuthenticationEventHandlerImpl())
                        )
                        .authorizationEndpoint(authorizationEndpoint -> authorizationEndpoint
                                // 授权码端点个性化confirm页面
                                .consentPage(SecurityConstants.CUSTOM_CONSENT_PAGE_URI)
                        ), Customizer.withDefaults())
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated());

        // 设置 Token 存储的策略
        http.with(authorizationServerConfigurer
                // redis存储token的实现
                .authorizationService(authorizationService)
                .authorizationServerSettings(AuthorizationServerSettings.builder()
                        .issuer(SecurityConstants.PROJECT_LICENSE).build()), Customizer.withDefaults());
        // 设置授权码模式登录页面
        http.with(new FormIdentityLoginConfigurer(), Customizer.withDefaults());
        DefaultSecurityFilterChain securityFilterChain = http.build();

        // 注入自定义授权模式实现
        addCustomOAuth2GrantAuthenticationProvider(http);
        return securityFilterChain;
    }

    /**
     * 令牌生成规则实现
     */
    @Bean
    @SuppressWarnings("rawtypes")
    public OAuth2TokenGenerator oAuth2TokenGenerator() {
        CustomTokenGenerator accessTokenGenerator = new CustomTokenGenerator();
        // 注入Token 增加关联用户信息
        accessTokenGenerator.setAccessTokenCustomizer(new CustomTokenCustomizer());
        return new DelegatingOAuth2TokenGenerator(accessTokenGenerator, new OAuth2RefreshTokenGenerator());
    }

    /**
     * Token注入请求转换器
     */
    private AuthenticationConverter accessTokenRequestConverter() {
        return new DelegatingAuthenticationConverter(Arrays.asList(
                new AuthenticationH5EmbedConverter(),
                new AuthenticationPasswordConverter(),
                new AuthenticationWechatMaConverter(),
                new AuthenticationTiktokMaConverter(),
                new AuthenticationAlipayMaConverter(),
                new OAuth2RefreshTokenAuthenticationConverter(),
                new OAuth2ClientCredentialsAuthenticationConverter(),
                new OAuth2AuthorizationCodeAuthenticationConverter(),
                new OAuth2AuthorizationCodeRequestAuthenticationConverter()));
    }

    /**
     * 自定义授权模式
     */
    @SuppressWarnings("unchecked")
    private void addCustomOAuth2GrantAuthenticationProvider(HttpSecurity http) {
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
        OAuth2AuthorizationService authorizationService = http.getSharedObject(OAuth2AuthorizationService.class);

        http.authenticationProvider(new AuthenticationProvider());

        // 密码模式
        AuthenticationPasswordProvider authenticationPasswordProvider = new AuthenticationPasswordProvider(authenticationManager, authorizationService, oAuth2TokenGenerator());
        http.authenticationProvider(authenticationPasswordProvider);

        // 微信小程序模式
        AuthenticationWechatMaProvider authenticationWechatMaProvider = new AuthenticationWechatMaProvider(authenticationManager, authorizationService, oAuth2TokenGenerator());
        http.authenticationProvider(authenticationWechatMaProvider);

        // 抖音小程序模式
        AuthenticationTiktokMaProvider authenticationTiktokMaProvider = new AuthenticationTiktokMaProvider(authenticationManager, authorizationService, oAuth2TokenGenerator());
        http.authenticationProvider(authenticationTiktokMaProvider);

        // 支付宝小程序模式
        AuthenticationAlipayMaProvider authenticationAlipayMaProvider = new AuthenticationAlipayMaProvider(authenticationManager, authorizationService, oAuth2TokenGenerator());
        http.authenticationProvider(authenticationAlipayMaProvider);

        // H5嵌入模式
        AuthenticationH5EmbedProvider authenticationH5EmbedProvider = new AuthenticationH5EmbedProvider(authenticationManager, authorizationService, oAuth2TokenGenerator());
        http.authenticationProvider(authenticationH5EmbedProvider);
    }
}
