package com.kk.mumuchat.common.liquibase.config;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import liquibase.integration.spring.SpringLiquibase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ResourceLoader;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Liquibase配置类，使用Nacos中的数据库配置
 *
 * @author mumuchat
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@EnableConfigurationProperties(LiquibaseProperties.class)
public class LiquibaseConfig {

    private static final Logger logger = LoggerFactory.getLogger(LiquibaseConfig.class);

    @Resource
    private DataSource dataSource;

    /**
     * 启动时打印Nacos配置信息
     */
    @PostConstruct
    public void logNacosConfig()  {
        try {
            logger.info("DataSource URL: {}", dataSource.getConnection().getMetaData().getURL());
            logger.info("DataSource Username: {}", dataSource.getConnection().getMetaData().getUserName());
        } catch (SQLException e) {
            logger.error("Failed to retrieve DataSource metadata", e);
        }    }

    /**
     * 自定义Liquibase配置
     */
    @Bean
    public SpringLiquibase liquibase(LiquibaseProperties liquibaseProperties,
                                     ResourceLoader resourceLoader) {
        try {
            SpringLiquibase liquibase = new SpringLiquibase();
            liquibase.setDataSource(dataSource);

            // 设置变更日志路径，默认值为 db/changelog/db.changelog-master.yaml
            liquibase.setChangeLog(getOrDefault(liquibaseProperties.getChangeLog(), "classpath:db/changelog/db.changelog-master.yaml"));

            liquibase.setDefaultSchema(liquibaseProperties.getDefaultSchema());
            liquibase.setDropFirst(liquibaseProperties.isDropFirst());
            liquibase.setShouldRun(liquibaseProperties.isEnabled());
            liquibase.setChangeLogParameters(liquibaseProperties.getParameters());
            liquibase.setRollbackFile(liquibaseProperties.getRollbackFile());
            liquibase.setResourceLoader(resourceLoader);

            return liquibase;
        } catch (Exception e) {
            logger.error("Failed to initialize Liquibase", e);
            throw new RuntimeException("Failed to initialize Liquibase", e);
        }
    }

    /**
     * 获取默认值的辅助方法
     *
     * @param value 实际值
     * @param defaultValue 默认值
     * @return 如果实际值为空则返回默认值，否则返回实际值
     */
    private String getOrDefault(String value, String defaultValue) {

        return (value == null || value.isEmpty()) ? defaultValue : value;
    }
}
