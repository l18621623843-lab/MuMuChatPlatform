package com.kk.mumuchat.system.api.organize.feign;

import com.kk.mumuchat.common.core.constant.basic.SecurityConstants;
import com.kk.mumuchat.common.core.web.result.R;
import com.kk.mumuchat.system.api.organize.domain.dto.SysPostDto;
import com.kk.mumuchat.system.api.organize.feign.factory.RemotePostFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 系统服务|组织模块|岗位服务
 *
 * @author mumuchat
 */
@FeignClient(contextId = "remotePostService", path = "/inner/post", value = "${xueyi.remote.service.system}", fallbackFactory = RemotePostFallbackFactory.class)
public interface RemotePostService {

    /**
     * 新增岗位
     *
     * @param post         岗位对象
     * @return 结果
     */
    @PostMapping(headers = SecurityConstants.FROM_SOURCE_INNER)
    R<SysPostDto> addInner(@RequestBody SysPostDto post);
}