package com.kk.mumuchat.common.security.config.properties;

import com.kk.mumuchat.common.core.utils.core.CollUtil;
import com.kk.mumuchat.common.core.utils.core.ReUtil;
import com.kk.mumuchat.common.core.utils.core.SpringUtil;
import com.kk.mumuchat.common.core.utils.core.StrUtil;
import com.kk.mumuchat.common.security.annotation.AdminAuth;
import com.kk.mumuchat.common.security.annotation.ApiAuth;
import com.kk.mumuchat.common.security.annotation.ExternalAuth;
import com.kk.mumuchat.common.security.annotation.InnerAuth;
import com.kk.mumuchat.common.security.annotation.MemberAuth;
import com.kk.mumuchat.common.security.annotation.MerchantAuth;
import com.kk.mumuchat.common.security.annotation.PlatformAuth;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * 匿名访问控制
 *
 * @author mumuchat
 */
@Setter
@Getter
@Slf4j
@ConfigurationProperties(prefix = "security.oauth2.ignore.whites")
public class PermitAllUrlProperties implements InitializingBean {

    private static final Pattern PATTERN = Pattern.compile("\\{(.*?)\\}");

    private static final String[] DEFAULT_IGNORE_URLS = new String[]{"/actuator/**",
            "/error", "/v3/api-docs", "/v3/api-docs/*", "/doc.html", "/webjars/**"};

    /** 常规全部 */
    private List<String> routine = new ArrayList<>();

    /** 自定义 */
    private MultiValueMap<RequestMethod, String> custom = new LinkedMultiValueMap<>();

    @Override
    public void afterPropertiesSet() {
        routine.addAll(Arrays.asList(DEFAULT_IGNORE_URLS));
        RequestMappingHandlerMapping mapping = SpringUtil.getBean("requestMappingHandlerMapping");
        Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();

        map.keySet().forEach(info -> {
            HandlerMethod handlerMethod = map.get(info);

            // 内部认证
            Optional.ofNullable(AnnotationUtils.findAnnotation(handlerMethod.getBeanType(), InnerAuth.class))
                    .filter(InnerAuth::isAnonymous)
                    .ifPresent(inner -> anonymousFilter(info));
            Optional.ofNullable(AnnotationUtils.findAnnotation(handlerMethod.getMethod(), InnerAuth.class))
                    .filter(InnerAuth::isAnonymous)
                    .ifPresent(inner -> anonymousFilter(info));

            // 外系统端认证
            Optional.ofNullable(AnnotationUtils.findAnnotation(handlerMethod.getBeanType(), ExternalAuth.class))
                    .filter(ExternalAuth::isAnonymous)
                    .ifPresent(inner -> anonymousFilter(info));
            Optional.ofNullable(AnnotationUtils.findAnnotation(handlerMethod.getMethod(), ExternalAuth.class))
                    .filter(ExternalAuth::isAnonymous)
                    .ifPresent(inner -> anonymousFilter(info));

            // 对外Api认证
            Optional.ofNullable(AnnotationUtils.findAnnotation(handlerMethod.getBeanType(), ApiAuth.class))
                    .filter(ApiAuth::isAnonymous)
                    .ifPresent(inner -> anonymousFilter(info));
            Optional.ofNullable(AnnotationUtils.findAnnotation(handlerMethod.getMethod(), ApiAuth.class))
                    .filter(ApiAuth::isAnonymous)
                    .ifPresent(inner -> anonymousFilter(info));

            // 管理端认证
            Optional.ofNullable(AnnotationUtils.findAnnotation(handlerMethod.getBeanType(), AdminAuth.class))
                    .filter(AdminAuth::isAnonymous)
                    .ifPresent(inner -> anonymousFilter(info));
            Optional.ofNullable(AnnotationUtils.findAnnotation(handlerMethod.getMethod(), AdminAuth.class))
                    .filter(AdminAuth::isAnonymous)
                    .ifPresent(inner -> anonymousFilter(info));

            // 平台端认证
            Optional.ofNullable(AnnotationUtils.findAnnotation(handlerMethod.getBeanType(), PlatformAuth.class))
                    .filter(PlatformAuth::isAnonymous)
                    .ifPresent(inner -> anonymousFilter(info));
            Optional.ofNullable(AnnotationUtils.findAnnotation(handlerMethod.getMethod(), PlatformAuth.class))
                    .filter(PlatformAuth::isAnonymous)
                    .ifPresent(inner -> anonymousFilter(info));

            // 会员端认证
            Optional.ofNullable(AnnotationUtils.findAnnotation(handlerMethod.getBeanType(), MemberAuth.class))
                    .filter(MemberAuth::isAnonymous)
                    .ifPresent(inner -> anonymousFilter(info));
            Optional.ofNullable(AnnotationUtils.findAnnotation(handlerMethod.getMethod(), MemberAuth.class))
                    .filter(MemberAuth::isAnonymous)
                    .ifPresent(inner -> anonymousFilter(info));

            // 商户端认证
            Optional.ofNullable(AnnotationUtils.findAnnotation(handlerMethod.getBeanType(), MerchantAuth.class))
                    .filter(MerchantAuth::isAnonymous)
                    .ifPresent(inner -> anonymousFilter(info));
            Optional.ofNullable(AnnotationUtils.findAnnotation(handlerMethod.getMethod(), MerchantAuth.class))
                    .filter(MerchantAuth::isAnonymous)
                    .ifPresent(inner -> anonymousFilter(info));
        });
    }

    private void anonymousFilter(RequestMappingInfo info) {
        Set<String> pathUrls = Optional.of(info).map(RequestMappingInfo::getDirectPaths).filter(CollUtil::isNotEmpty).orElseGet(info::getPatternValues);
        Optional.of(pathUrls).filter(CollUtil::isNotEmpty)
                .map(list -> list.stream().map(url -> ReUtil.replaceAll(url, PATTERN, StrUtil.ASTERISK)).toList())
                .filter(CollUtil::isNotEmpty)
                .ifPresent(urls ->
                        Optional.of(info).map(RequestMappingInfo::getMethodsCondition)
                                .map(RequestMethodsRequestCondition::getMethods)
                                .filter(CollUtil::isNotEmpty)
                                .ifPresentOrElse(methods ->
                                                methods.forEach(method -> custom.addAll(method, urls)),
                                        () ->
                                                routine.addAll(urls)));
    }
}