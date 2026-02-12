package com.kk.mumuchat.common.redis.service;

import com.kk.mumuchat.common.core.exception.ServiceException;
import com.kk.mumuchat.common.core.exception.UtilException;
import com.kk.mumuchat.common.core.utils.core.NumberUtil;
import com.kk.mumuchat.common.core.utils.core.ObjectUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Redisson 工具类
 *
 * @author xueyi
 */
@Slf4j
@Getter
@Component
public class RedissonService {

    @Autowired
    public RedissonClient client;

    public static String lockStr = "lock:";

    /**
     * 限流
     *
     * @param key          限流key
     * @param rateType     限流类型
     * @param rate         速率
     * @param rateInterval 速率间隔（秒）
     * @return -1 表示失败
     */
    public long rateLimiter(String key, RateType rateType, int rate, int rateInterval) {
        RRateLimiter rateLimiter = client.getRateLimiter(key);
        rateLimiter.trySetRate(rateType, rate, Duration.ofSeconds(rateInterval));
        if (rateLimiter.tryAcquire()) {
            return rateLimiter.availablePermits();
        } else {
            return -1L;
        }
    }

    /**
     * 分布式锁方法 | Supplier
     *
     * @param lockCode 锁编码
     * @param supplier SupplierFun
     */
    public <T> T lockFun(String lockCode, Supplier<T> supplier) {
        return lockFun(lockCode, supplier, null);
    }

    /**
     * 分布式锁方法 | Supplier
     *
     * @param lockCode      锁编码
     * @param supplier      SupplierFun
     * @param errorConsumer 异常执行
     */
    public <T> T lockFun(String lockCode, Supplier<T> supplier, Consumer<Exception> errorConsumer) {
        return lockFun(lockCode, supplier, NumberUtil.Five, -1L, TimeUnit.MINUTES, errorConsumer);
    }

    /**
     * 分布式锁方法 | Supplier
     *
     * @param lockCode  锁编码
     * @param supplier  SupplierFun
     * @param waitTime  最长等待时间
     * @param leaseTime 最长锁定时间
     * @param timeUnit  时间类型
     */
    public <T> T lockFun(String lockCode, Supplier<T> supplier, long waitTime, long leaseTime, TimeUnit timeUnit) {
        return lockFun(lockCode, supplier, waitTime, leaseTime, timeUnit, null);
    }

    /**
     * 分布式锁方法 | Supplier
     *
     * @param lockCode      锁编码
     * @param supplier      SupplierFun
     * @param waitTime      最长等待时间
     * @param leaseTime     最长锁定时间
     * @param timeUnit      时间类型
     * @param errorConsumer 异常执行
     */
    public <T> T lockFun(String lockCode, Supplier<T> supplier, long waitTime, long leaseTime, TimeUnit timeUnit, Consumer<Exception> errorConsumer) {
        RLock lock = null;
        try {
            lock = client.getLock(lockStr + lockCode);
            boolean tryLock = lock.tryLock(waitTime, leaseTime, timeUnit);
            if (tryLock) {
                return supplier.get();
            }
        } catch (ServiceException e) {
            if (ObjectUtil.isNotNull(errorConsumer)) {
                errorConsumer.accept(e);
            } else {
                throw new ServiceException(e.getMessage());
            }
        } catch (Exception e) {
            log.error("分布式锁Supplier方法执行失败：\n锁编码：{};\n错误原因：{};\n异常内容：", lockCode, e.getMessage(), e);
            throw new UtilException(e);
        } finally {
            if (lock != null && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
        return null;
    }

    /**
     * 分布式锁方法 | Runnable
     *
     * @param lockCode 锁编码
     * @param runnable RunnableFun
     */
    public void lockFun(String lockCode, Runnable runnable) {
        lockFun(lockCode, runnable, null);
    }

    /**
     * 分布式锁方法 | Runnable
     *
     * @param lockCode      锁编码
     * @param runnable      RunnableFun
     * @param errorConsumer 异常执行
     */
    public void lockFun(String lockCode, Runnable runnable, Consumer<Exception> errorConsumer) {
        lockFun(lockCode, runnable, NumberUtil.Five, -1L, TimeUnit.MINUTES, errorConsumer);
    }

    /**
     * 分布式锁方法 | Runnable
     *
     * @param lockCode  锁编码
     * @param runnable  RunnableFun
     * @param waitTime  最长等待时间
     * @param leaseTime 最长锁定时间
     * @param timeUnit  时间类型
     */
    public void lockFun(String lockCode, Runnable runnable, long waitTime, long leaseTime, TimeUnit timeUnit) {
        lockFun(lockCode, runnable, waitTime, leaseTime, timeUnit, null);
    }

    /**
     * 分布式锁方法 | Runnable
     *
     * @param lockCode      锁编码
     * @param runnable      RunnableFun
     * @param waitTime      最长等待时间
     * @param leaseTime     最长锁定时间
     * @param timeUnit      时间类型
     * @param errorConsumer 异常执行
     */
    public void lockFun(String lockCode, Runnable runnable, long waitTime, long leaseTime, TimeUnit timeUnit, Consumer<Exception> errorConsumer) {
        RLock lock = null;
        try {
            lock = client.getLock(lockStr + lockCode);
            boolean tryLock = lock.tryLock(waitTime, leaseTime, timeUnit);
            if (tryLock) {
                runnable.run();
            }
        } catch (ServiceException e) {
            if (ObjectUtil.isNotNull(errorConsumer)) {
                errorConsumer.accept(e);
            } else {
                throw new ServiceException(e.getMessage());
            }
        } catch (Exception e) {
            log.error("分布式锁Runnable方法执行失败：\n锁编码：{};\n错误原因：{};\n异常内容：", lockCode, e.getMessage(), e);
            throw new UtilException(e);
        } finally {
            if (lock != null && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }
}
