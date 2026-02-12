package com.kk.mumuchat.common.redis.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * redis 对象缓存管理实现类
 *
 * @author xueyi
 **/
@Getter
@Component
@SuppressWarnings(value = {"rawtypes"})
public class RedisJavaService extends RedisBaseService {

    @Autowired
    @Qualifier("redisJavaTemplate")
    public RedisTemplate redisTemplate;

}
