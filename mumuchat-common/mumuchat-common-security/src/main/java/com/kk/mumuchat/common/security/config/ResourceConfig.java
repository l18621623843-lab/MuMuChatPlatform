package com.kk.mumuchat.common.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kk.mumuchat.common.security.auth.Auth;
import com.kk.mumuchat.common.security.auth.AuthService;
import com.kk.mumuchat.common.security.config.properties.PermitAllUrlProperties;
import com.kk.mumuchat.common.security.handler.BearerTokenHandler;
import com.kk.mumuchat.common.security.handler.OpaqueTokenHandler;
import com.kk.mumuchat.common.security.handler.ResourceAuthenticationHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.annotation.AnnotationTemplateExpressionDefaults;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;

/**
 * 资源服务配置
 *
 * @author mumuchat
 */
@RequiredArgsConstructor
@EnableConfigurationProperties(PermitAllUrlProperties.class)
public class ResourceConfig {

    /**
     * 权限验证
     */
    @Bean("ss")
    public AuthService authService() {
        return new AuthService();
    }

    /**
     * 权限标识常量
     */
    @Bean("Auth")
    public Auth auth() {
        return new Auth();
    }

    /**
     * 请求令牌的抽取逻辑
     *
     * @param urlProperties 对外暴露的接口列表
     */
    @Bean
    public BearerTokenHandler bearerTokenHandler(PermitAllUrlProperties urlProperties) {
        return new BearerTokenHandler(urlProperties);
    }

    /**
     * 资源服务器异常处理
     *
     * @param objectMapper jackson 输出对象
     */
    @Bean
    public ResourceAuthenticationHandler resourceAuthExceptionEntryPoint(ObjectMapper objectMapper) {
        return new ResourceAuthenticationHandler(objectMapper);
    }


    /**
     * 资源服务器token处理器
     *
     * @param authorizationService token 存储实现
     */
    @Bean
    public OpaqueTokenIntrospector opaqueTokenIntrospector(OAuth2AuthorizationService authorizationService) {
        return new OpaqueTokenHandler(authorizationService);
    }

    /**
     * 支持自定义权限表达式
     *
     * @return {@link AnnotationTemplateExpressionDefaults }
     */
    @Bean
    public AnnotationTemplateExpressionDefaults prePostTemplateDefaults() {
        return new AnnotationTemplateExpressionDefaults();
    }
}
