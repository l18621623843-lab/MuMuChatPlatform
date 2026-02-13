package com.kk.mumuchat.common.security.annotation;

import com.kk.mumuchat.common.security.config.ResourceConfig;
import com.kk.mumuchat.common.security.config.SecurityConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 资源服务注解
 *
 * @author mumuchat
 */
@Inherited
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({ResourceConfig.class, SecurityConfig.class})
public @interface EnableResourceServer {
}
