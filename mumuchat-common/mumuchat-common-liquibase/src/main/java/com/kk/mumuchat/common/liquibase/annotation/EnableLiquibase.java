package com.kk.mumuchat.common.liquibase.annotation;

import com.kk.mumuchat.common.liquibase.config.LiquibaseConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用Liquibase配置注解
 *
 * @author xueyi
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(LiquibaseConfig.class)
public @interface EnableLiquibase {
}