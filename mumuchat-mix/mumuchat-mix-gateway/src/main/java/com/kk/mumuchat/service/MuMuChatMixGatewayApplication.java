package com.kk.mumuchat.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * 融合网关模块
 *
 * @author mumuchat
 */
@ComponentScan(basePackages = {
        "com.kk.mumuchat.gateway",
        "com.kk.mumuchat.service"
})
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class MuMuChatMixGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(MuMuChatMixGatewayApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  融合网关模块启动成功   ლ(´ڡ`ლ)ﾞ  ");
    }
}