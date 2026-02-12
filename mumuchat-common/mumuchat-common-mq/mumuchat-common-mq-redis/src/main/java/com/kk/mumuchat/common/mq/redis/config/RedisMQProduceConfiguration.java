package com.kk.mumuchat.common.mq.redis.config;

import com.kk.mumuchat.common.mq.redis.core.RedisMQTemplate;
import com.kk.mumuchat.common.mq.redis.core.interceptor.RedisMessageInterceptor;
import com.kk.mumuchat.common.redis.configure.RedisConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;

/**
 * Redis 消息队列 Producer 配置类
 *
 * @author xueyi
 */
@Slf4j
@AutoConfiguration(after = RedisConfig.class)
public class RedisMQProduceConfiguration {

    @Bean
    public RedisMQTemplate redisMQTemplate(StringRedisTemplate redisTemplate, List<RedisMessageInterceptor> interceptors) {
        RedisMQTemplate redisMQTemplate = new RedisMQTemplate(redisTemplate);
        // 添加拦截器
        interceptors.forEach(redisMQTemplate::addInterceptor);
        return redisMQTemplate;
    }

}
