package com.kk.mumuchat.file;

import com.github.tobato.fastdfs.FdfsClientConfig;
import com.kk.mumuchat.common.security.annotation.EnableResourceServer;
import com.kk.mumuchat.common.security.annotation.EnableRyFeignClients;
import com.kk.mumuchat.common.security.config.ApplicationConfig;
import com.kk.mumuchat.common.security.config.JacksonConfig;
import com.kk.mumuchat.common.swagger.annotation.EnableCustomSwagger;
import com.kk.mumuchat.file.opencv.OpenCVLoader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Import;

/**
 * 文件服务
 *
 * @author mumuchat
 */
@EnableCustomSwagger
@EnableResourceServer
@EnableRyFeignClients
@Import({ApplicationConfig.class, JacksonConfig.class, FdfsClientConfig.class})
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class MuMuChatFileApplication {
    public static void main(String[] args) {
        try {
            // 加载OpenCVLoader
            OpenCVLoader.init();
            OpenCVLoader.isLoaded = true;
        } catch (Exception e) {
            System.out.println("【OpenCV】启动失败");
        }
        SpringApplication.run(MuMuChatFileApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  文件服务模块启动成功   ლ(´ڡ`ლ)ﾞ");
    }
}
