package com.kk.mumuchat.common.web.config;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.parser.JsqlParserGlobal;
import com.baomidou.mybatisplus.extension.parser.cache.JdkSerialCaffeineJsqlParseCache;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.DataPermissionInterceptor;
import com.github.pagehelper.PageInterceptor;
import com.kk.mumuchat.common.datascope.interceptor.XueYiDataScopeHandler;
import com.kk.mumuchat.common.web.handler.TenantLineHandler;
import com.kk.mumuchat.common.web.handler.XueYiMetaObjectHandler;
import com.kk.mumuchat.common.web.injector.CustomizedSqlInjector;
import com.kk.mumuchat.common.web.interceptor.TenantLineInnerInterceptor;
import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.TimeUnit;

/**
 * MP配置
 *
 * @author xueyi
 */
@AutoConfiguration(before = MybatisPlusAutoConfiguration.class)
public class XueYiMyBatisPlusConfig {

    @Resource
    private XueYiDataScopeHandler dataScopeAspect;

    @Resource
    private TenantLineHandler tenantLineHandler;

    static {
        // 动态 SQL 智能优化支持本地缓存加速解析，更完善的租户复杂 XML 动态 SQL 支持，静态注入缓存
        JsqlParserGlobal.setJsqlParseCache(new JdkSerialCaffeineJsqlParseCache(
                (cache) -> cache.maximumSize(1024)
                        .expireAfterWrite(5, TimeUnit.SECONDS))
        );
    }

    /**
     * PageHelper分页配置
     */
    @Bean
    public PageInterceptor pageInterceptor() {
        return new PageInterceptor();
    }

    /**
     * 方法注入
     */
    @Bean
    public CustomizedSqlInjector customizedSqlInjector() {
        return new CustomizedSqlInjector();
    }

    /**
     * 自定义Id生成器
     */
    @Bean
    public XueYiCustomIdGenerator customIdGenerator() {
        return new XueYiCustomIdGenerator();
    }

    /**
     * 自动填充
     */
    @Bean
    public MetaObjectHandler defaultMetaObjectHandler() {
        return new XueYiMetaObjectHandler();
    }

    /**
     * 插件配置
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 数据权限插件
        interceptor.addInnerInterceptor(new DataPermissionInterceptor(dataScopeAspect));
        // 租户控制插件
        interceptor.addInnerInterceptor(new TenantLineInnerInterceptor<>(tenantLineHandler));
        // 禁全表更删插件
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        return interceptor;
    }
}
