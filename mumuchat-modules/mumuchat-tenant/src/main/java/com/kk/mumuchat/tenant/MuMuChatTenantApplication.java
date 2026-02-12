package com.kk.mumuchat.tenant;

import com.kk.mumuchat.common.liquibase.annotation.EnableLiquibase;
import com.kk.mumuchat.common.security.annotation.EnableCustomConfig;
import com.kk.mumuchat.common.security.annotation.EnableResourceServer;
import com.kk.mumuchat.common.security.annotation.EnableRyFeignClients;
import com.kk.mumuchat.common.swagger.annotation.EnableCustomSwagger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 租户模块
 *
 * @author xueyi
 */
@EnableLiquibase
@EnableCustomConfig
@EnableCustomSwagger
@EnableResourceServer
@EnableRyFeignClients
@SpringBootApplication
public class MuMuChatTenantApplication {
    public static void main(String[] args) {
        SpringApplication.run(MuMuChatTenantApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  租户管理模块启动成功   ლ(´ڡ`ლ)ﾞ  \n" +
                "  _____     __   ____     __        \n" +
                "  \\   _\\   /  /  \\   \\   /  /   \n" +
                "  .-./ ). /  '    \\  _. /  '       \n" +
                "  \\ '_ .') .'      _( )_ .'        \n" +
                " (_ (_) _) '   ___(_ o _)'          \n" +
                "   /    \\   \\ |   |(_,_)'         \n" +
                "   `-'`-'    \\|   `-'  /           \n" +
                "  /  /   \\    \\\\      /          \n" +
                " '--'     '----'`-..-'              ");
    }
}