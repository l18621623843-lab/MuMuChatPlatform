package com.kk.mumuchat.common.security.aspect;

import com.kk.mumuchat.common.core.constant.basic.SecurityConstants;
import com.kk.mumuchat.common.core.exception.InnerAuthException;
import com.kk.mumuchat.common.core.utils.core.ObjectUtil;
import com.kk.mumuchat.common.core.utils.core.StrUtil;
import com.kk.mumuchat.common.core.utils.servlet.ServletUtil;
import com.kk.mumuchat.common.security.annotation.AdminAuth;
import com.kk.mumuchat.common.security.annotation.ApiAuth;
import com.kk.mumuchat.common.security.annotation.ExternalAuth;
import com.kk.mumuchat.common.security.annotation.InnerAuth;
import com.kk.mumuchat.common.security.annotation.MemberAuth;
import com.kk.mumuchat.common.security.annotation.MerchantAuth;
import com.kk.mumuchat.common.security.annotation.PlatformAuth;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 服务调用验证处理
 *
 * @author xueyi
 */
@Slf4j
@Aspect
@Component
public class AuthAspect implements Ordered {

    @Pointcut(value = "@within(com.kk.mumuchat.common.security.annotation.InnerAuth) || @annotation(com.kk.mumuchat.common.security.annotation.InnerAuth)")
    public void innerAuthCut() {
    }

    /**
     * 内部认证校验
     */
    @SneakyThrows
    @Around(value = "innerAuthCut()")
    public Object innerAuthAround(ProceedingJoinPoint point) {
        InnerAuth innerAuth = getAnnotation(point, InnerAuth.class);
        HttpServletRequest request = ServletUtil.getRequest();
        Assert.notNull(request, "request cannot be null");
        String source = request.getHeader(SecurityConstants.FROM_SOURCE);
        // 内部请求验证
        if (!StrUtil.equals(SecurityConstants.INNER, source)) {
            log.warn("请求地址'{}'，没有内部访问权限", request.getRequestURI());
            throw new InnerAuthException("没有内部访问权限，不允许访问");
        }

        if (!innerAuth.isAnonymous()) {
            // 用户信息验证
            if (ObjectUtil.isNotNull(innerAuth) && innerAuth.isUser()) {
                String enterpriseId = request.getHeader(SecurityConstants.BaseSecurity.ENTERPRISE_ID.getBaseCode());
                String userId = request.getHeader(SecurityConstants.BaseSecurity.USER_ID.getBaseCode());
                String accountType = request.getHeader(SecurityConstants.BaseSecurity.ACCOUNT_TYPE.getBaseCode());
                if ((StrUtil.hasBlank(enterpriseId, userId, accountType))) {
                    log.warn("请求地址'{}'，没有设置用户信息", request.getRequestURI());
                    throw new InnerAuthException("没有设置用户信息，不允许访问");
                }
            }
        }
        return point.proceed();
    }

    @Pointcut(value = "@within(com.kk.mumuchat.common.security.annotation.AdminAuth) || @annotation(com.kk.mumuchat.common.security.annotation.AdminAuth)")
    public void adminAuthCut() {
    }

    /**
     * 管理端认证校验
     */
    @SneakyThrows
    @Around("adminAuthCut()")
    public Object adminAuthAround(ProceedingJoinPoint point) {
        AdminAuth adminAuth = getAnnotation(point, AdminAuth.class);
        HttpServletRequest request = ServletUtil.getRequest();
        Assert.notNull(request, "request cannot be null");
        if (!adminAuth.isAnonymous()) {
            String accountType = request.getHeader(SecurityConstants.BaseSecurity.ACCOUNT_TYPE.getBaseCode());
            if (StrUtil.notEquals(SecurityConstants.AccountType.ADMIN.getCode(), accountType)) {
                log.warn("请求地址'{}'，没有管理端访问权限", request.getRequestURI());
                throw new InnerAuthException("没有管理端访问权限，不允许访问");
            }
            // 用户信息验证
            if (ObjectUtil.isNotNull(adminAuth) && adminAuth.isUser()) {
                String enterpriseId = request.getHeader(SecurityConstants.BaseSecurity.ENTERPRISE_ID.getBaseCode());
                String userId = request.getHeader(SecurityConstants.BaseSecurity.USER_ID.getBaseCode());
                if ((StrUtil.hasBlank(enterpriseId, userId, accountType))) {
                    log.warn("请求地址'{}'，没有设置用户信息", request.getRequestURI());
                    throw new InnerAuthException("没有设置用户信息，不允许访问");
                }
            }
        }
        return point.proceed();
    }


    @Pointcut(value = "@within(com.kk.mumuchat.common.security.annotation.MemberAuth) || @annotation(com.kk.mumuchat.common.security.annotation.MemberAuth)")
    public void memberAuthCut() {
    }

    /**
     * 会员端认证校验
     */
    @SneakyThrows
    @Around("memberAuthCut()")
    public Object memberAuthAround(ProceedingJoinPoint point) {
        MemberAuth memberAuth = getAnnotation(point, MemberAuth.class);
        HttpServletRequest request = ServletUtil.getRequest();
        Assert.notNull(request, "request cannot be null");
        if (!memberAuth.isAnonymous()) {
            String accountType = request.getHeader(SecurityConstants.BaseSecurity.ACCOUNT_TYPE.getBaseCode());
            if (StrUtil.notEquals(SecurityConstants.AccountType.MEMBER.getCode(), accountType)) {
                log.warn("请求地址'{}'，没有会员端访问权限", request.getRequestURI());
                throw new InnerAuthException("没有会员端访问权限，不允许访问");
            }

            // 用户信息验证
            if (ObjectUtil.isNotNull(memberAuth) && memberAuth.isUser()) {
                String enterpriseId = request.getHeader(SecurityConstants.BaseSecurity.ENTERPRISE_ID.getBaseCode());
                String memberId = request.getHeader(SecurityConstants.BaseSecurity.USER_ID.getBaseCode());
                if ((StrUtil.hasBlank(enterpriseId, memberId, accountType))) {
                    log.warn("请求地址'{}'，没有设置用户信息", request.getRequestURI());
                    throw new InnerAuthException("没有设置用户信息，不允许访问");
                }
            }
        }
        return point.proceed();
    }

    @Pointcut(value = "@within(com.kk.mumuchat.common.security.annotation.PlatformAuth) || @annotation(com.kk.mumuchat.common.security.annotation.PlatformAuth)")
    public void platformAuthCut() {
    }

    /**
     * 平台端认证校验
     */
    @SneakyThrows
    @Around("platformAuthCut()")
    public Object platformAuthAround(ProceedingJoinPoint point) {
        PlatformAuth platformAuth = getAnnotation(point, PlatformAuth.class);
        HttpServletRequest request = ServletUtil.getRequest();
        Assert.notNull(request, "request cannot be null");
        if (!platformAuth.isAnonymous()) {
            String accountType = request.getHeader(SecurityConstants.BaseSecurity.ACCOUNT_TYPE.getBaseCode());
            if (StrUtil.notEquals(SecurityConstants.AccountType.MEMBER.getCode(), accountType)) {
                log.warn("请求地址'{}'，没有平台端访问权限", request.getRequestURI());
                throw new InnerAuthException("没有平台端访问权限，不允许访问");
            }
            // 用户信息验证
            if (ObjectUtil.isNotNull(platformAuth) && platformAuth.isUser()) {
                String enterpriseId = request.getHeader(SecurityConstants.BaseSecurity.ENTERPRISE_ID.getBaseCode());
                String applicationId = request.getHeader(SecurityConstants.MemberSecurity.APPLICATION_ID.getBaseCode());
                if ((StrUtil.hasBlank(enterpriseId, applicationId, accountType))) {
                    log.warn("请求地址'{}'，没有设置平台信息", request.getRequestURI());
                    throw new InnerAuthException("没有设置平台信息，不允许访问");
                }
            }
        }
        return point.proceed();
    }

    @Pointcut(value = "@within(com.kk.mumuchat.common.security.annotation.MerchantAuth) || @annotation(com.kk.mumuchat.common.security.annotation.MerchantAuth)")
    public void merchantAuthCut() {
    }

    /**
     * 商户端认证校验
     */
    @SneakyThrows
    @Around("merchantAuthCut()")
    public Object merchantAuthAround(ProceedingJoinPoint point) {
        MerchantAuth merchantAuth = getAnnotation(point, MerchantAuth.class);
        HttpServletRequest request = ServletUtil.getRequest();
        Assert.notNull(request, "request cannot be null");
        if (!merchantAuth.isAnonymous()) {
            String accountType = request.getHeader(SecurityConstants.BaseSecurity.ACCOUNT_TYPE.getBaseCode());
            if (StrUtil.notEquals(SecurityConstants.AccountType.MERCHANT.getCode(), accountType)) {
                log.warn("请求地址'{}'，没有商户端访问权限", request.getRequestURI());
                throw new InnerAuthException("没有商户端访问权限，不允许访问");
            }
            // 用户信息验证
            if (ObjectUtil.isNotNull(merchantAuth) && merchantAuth.isUser()) {
                String enterpriseId = request.getHeader(SecurityConstants.BaseSecurity.ENTERPRISE_ID.getBaseCode());
                if ((StrUtil.hasBlank(enterpriseId, accountType))) {
                    log.warn("请求地址'{}'，没有设置商户信息", request.getRequestURI());
                    throw new InnerAuthException("没有设置商户信息，不允许访问");
                }
            }
        }
        return point.proceed();
    }

    @Pointcut(value = "@within(com.kk.mumuchat.common.security.annotation.ExternalAuth) || @annotation(com.kk.mumuchat.common.security.annotation.ExternalAuth)")
    public void externalAuthCut() {
    }

    /**
     * 外系统端认证校验
     */
    @SneakyThrows
    @Around("externalAuthCut()")
    public Object externalAuthAround(ProceedingJoinPoint point) {
        ExternalAuth externalAuth = getAnnotation(point, ExternalAuth.class);
        HttpServletRequest request = ServletUtil.getRequest();
        Assert.notNull(request, "request cannot be null");
        if (!externalAuth.isAnonymous()) {
            String accountType = request.getHeader(SecurityConstants.BaseSecurity.ACCOUNT_TYPE.getBaseCode());
            if (StrUtil.notEquals(SecurityConstants.AccountType.EXTERNAL.getCode(), accountType)) {
                log.warn("请求地址'{}'，没有外系统端访问权限", request.getRequestURI());
                throw new InnerAuthException("没有外系统端访问权限，不允许访问");
            }
        }
        return point.proceed();
    }

    @Pointcut(value = "@within(com.kk.mumuchat.common.security.annotation.ApiAuth) || @annotation(com.kk.mumuchat.common.security.annotation.ApiAuth)")
    public void apiAuthCut() {
    }

    /**
     * Api端认证校验
     */
    @SneakyThrows
    @Around("apiAuthCut()")
    public Object apiAuthAround(ProceedingJoinPoint point) {
        ApiAuth apiAuth = getAnnotation(point, ApiAuth.class);
        HttpServletRequest request = ServletUtil.getRequest();
        Assert.notNull(request, "request cannot be null");
        return point.proceed();
    }

    /**
     * 通过连接点对象获取其方法或类上的注解
     *
     * @param point     连接点
     * @param authClass 注解类型
     * @return 注解
     */
    private <T extends Annotation> T getAnnotation(ProceedingJoinPoint point, Class<T> authClass) {
        T auth = null;
        Signature signature = point.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (ObjectUtil.isNotNull(method)) {
            if (method.isAnnotationPresent(authClass)) {
                auth = method.getAnnotation(authClass);
            }
            if (ObjectUtil.isNull(auth) && method.getDeclaringClass().isAnnotationPresent(authClass)) {
                auth = method.getDeclaringClass().getAnnotation(authClass);
            }
        }
        return auth;
    }

    /**
     * 确保在权限认证aop执行前执行
     */
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 1;
    }
}
