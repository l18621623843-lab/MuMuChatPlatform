package com.kk.mumuchat.common.security.annotation;

import com.kk.mumuchat.common.security.feign.FeignAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义feign注解
 *
 * @author mumuchat
 */
@Inherited
@Documented
@EnableFeignClients
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({FeignAutoConfiguration.class})
public @interface EnableRyFeignClients {

    String[] value() default {};

    String[] basePackages() default {"com.kk.mumuchat"};

    Class<?>[] basePackageClasses() default {};

    Class<?>[] defaultConfiguration() default {};

    Class<?>[] clients() default {};
}
