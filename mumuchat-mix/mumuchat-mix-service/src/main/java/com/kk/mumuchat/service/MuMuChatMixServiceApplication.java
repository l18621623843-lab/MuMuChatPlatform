package com.kk.mumuchat.service;

import com.github.tobato.fastdfs.FdfsClientConfig;
import com.kk.mumuchat.common.security.annotation.EnableCustomConfig;
import com.kk.mumuchat.common.security.annotation.EnableResourceServer;
import com.kk.mumuchat.common.security.annotation.EnableRyFeignClients;
import com.kk.mumuchat.common.swagger.annotation.EnableCustomSwagger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

/**
 * 融合业务模块
 *
 * @author mumuchat
 */
@EnableCustomConfig
@EnableCustomSwagger
@EnableResourceServer
@EnableRyFeignClients
@ComponentScan(basePackages = {
        "com.kk.mumuchat.auth",
        "com.kk.mumuchat.system",
        "com.kk.mumuchat.tenant",
        "com.kk.mumuchat.job",
        "com.kk.mumuchat.file"
})
@Import({FdfsClientConfig.class})
@SpringBootApplication
public class MuMuChatMixServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(MuMuChatMixServiceApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  融合业务模块启动成功   ლ(´ڡ`ლ)ﾞ  \n" +
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