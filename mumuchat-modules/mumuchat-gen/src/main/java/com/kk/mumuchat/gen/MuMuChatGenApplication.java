package com.kk.mumuchat.gen;

import com.kk.mumuchat.common.security.annotation.EnableCustomConfig;
import com.kk.mumuchat.common.security.annotation.EnableResourceServer;
import com.kk.mumuchat.common.security.annotation.EnableRyFeignClients;
import com.kk.mumuchat.common.swagger.annotation.EnableCustomSwagger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 代码生成
 *
 * @author mumuchat
 */
@EnableCustomConfig
@EnableCustomSwagger
@EnableResourceServer
@EnableRyFeignClients
@SpringBootApplication
public class MuMuChatGenApplication {
    public static void main(String[] args) {
        SpringApplication.run(MuMuChatGenApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  代码生成模块启动成功   ლ(´ڡ`ლ)ﾞ ");
    }
}