package com.kk.mumuchat.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 网关启动程序
 *
 * @author mumuchat
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class MuMuChatGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(MuMuChatGatewayApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  网关模块启动成功   ლ(´ڡ`ლ)ﾞ  \n");
    }
}
