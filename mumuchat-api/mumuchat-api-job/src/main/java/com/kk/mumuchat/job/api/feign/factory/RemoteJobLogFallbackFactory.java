package com.kk.mumuchat.job.api.feign.factory;

import com.kk.mumuchat.common.core.web.result.R;
import com.kk.mumuchat.job.api.feign.RemoteJobLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 调度日志服务 降级处理
 *
 * @author xueyi
 */
@Slf4j
@Component
public class RemoteJobLogFallbackFactory implements FallbackFactory<RemoteJobLogService> {

    @Override
    public RemoteJobLogService create(Throwable throwable) {
        log.error("调度日志服务调用失败:{},失败原因：", throwable.getMessage(), throwable);
        return jobLog -> R.fail("调度日志保存失败");
    }
}