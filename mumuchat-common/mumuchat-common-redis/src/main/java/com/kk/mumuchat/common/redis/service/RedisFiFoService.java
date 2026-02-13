package com.kk.mumuchat.common.redis.service;

import com.kk.mumuchat.common.core.utils.core.ObjectUtil;
import jakarta.annotation.Resource;
import lombok.Getter;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * redis-FIFO队列 缓存管理实现类
 *
 * @author mumuchat
 **/
@Getter
@Component
@SuppressWarnings(value = {"unchecked", "rawtypes"})
public class RedisFiFoService {

    @Resource
    public RedisTemplate redisTemplate;

    /**
     * 添加元素（FIFO）
     *
     * @param key   键
     * @param value 值
     */
    public <T> void push(String key, T value) {
        redisTemplate.opsForList().leftPush(key, value);
    }

    public <T> long push(final String key, final T value, final long timeout, final TimeUnit unit) {
        Long count = redisTemplate.opsForList().leftPush(key, value);
        if (ObjectUtil.isNotNull(count)) {
            redisTemplate.expire(key, timeout, unit);
        }
        return count == null ? 0 : count;
    }

    /**
     * 从列表移除并返回元素（FIFO）
     *
     * @param key 键
     * @return 值
     */
    public <T> T pop(String key) {
        ListOperations<String, T> operation = redisTemplate.opsForList();
        return operation.rightPop(key);
    }
}
