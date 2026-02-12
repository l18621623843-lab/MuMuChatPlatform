package com.kk.mumuchat.job.api.feign;

import com.kk.mumuchat.common.core.constant.basic.SecurityConstants;
import com.kk.mumuchat.common.core.web.result.R;
import com.kk.mumuchat.job.api.domain.dto.SysJobLogDto;
import com.kk.mumuchat.job.api.feign.factory.RemoteJobLogFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 调度日志服务
 *
 * @author xueyi
 */
@FeignClient(contextId = "remoteJobLogService", path = "/inner/job/log", value = "${xueyi.remote.service.job}", fallbackFactory = RemoteJobLogFallbackFactory.class)
public interface RemoteJobLogService {

    /**
     * 保存调度日志
     *
     * @param jobLog       调度日志实体
     * @return 结果
     */

    @PostMapping(headers = SecurityConstants.FROM_SOURCE_INNER)
    R<Boolean> addInner(@RequestBody SysJobLogDto jobLog);
}