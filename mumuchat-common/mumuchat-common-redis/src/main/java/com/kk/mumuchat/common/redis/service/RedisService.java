package com.kk.mumuchat.common.redis.service;

import jakarta.annotation.Resource;
import lombok.Getter;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * redis 缓存管理实现类
 *
 * @author xueyi
 **/
@Getter
@Primary
@Component
@SuppressWarnings(value = {"rawtypes"})
public class RedisService extends RedisBaseService {

    @Resource
    public RedisTemplate redisTemplate;
}
