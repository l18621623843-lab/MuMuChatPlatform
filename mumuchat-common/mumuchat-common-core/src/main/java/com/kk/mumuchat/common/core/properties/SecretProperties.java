package com.kk.mumuchat.common.core.properties;

import lombok.Getter;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 安全配置
 *
 * @author xueyi
 */
@Configuration
@ConfigurationProperties(prefix = "xueyi.security.secret")
public class SecretProperties implements BeanPostProcessor {

    /** 网关地址 */
    @Getter
    private static String gatewayUrl;

    /** 令牌秘钥 */
    @Getter
    private static String token;

    /** 平台登录秘钥 */
    @Getter
    private static String platform;

    public void setToken(String token) {
        SecretProperties.token = token;
    }

    public void setPlatform(String platform) {
        SecretProperties.platform = platform;
    }

    public void setGatewayUrl(String gatewayUrl) {
        SecretProperties.gatewayUrl = gatewayUrl;
    }
}