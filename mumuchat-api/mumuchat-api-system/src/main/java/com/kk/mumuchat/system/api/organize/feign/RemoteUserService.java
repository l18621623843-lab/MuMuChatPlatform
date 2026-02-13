package com.kk.mumuchat.system.api.organize.feign;

import com.kk.mumuchat.common.core.constant.basic.SecurityConstants;
import com.kk.mumuchat.common.core.web.feign.RemoteSelectService;
import com.kk.mumuchat.common.core.web.result.R;
import com.kk.mumuchat.system.api.organize.domain.dto.SysUserDto;
import com.kk.mumuchat.system.api.organize.feign.factory.RemoteUserFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 系统服务|组织模块|用户服务
 *
 * @author mumuchat
 */
@FeignClient(contextId = "remoteUserService", path = "/inner/user", value = "${xueyi.remote.service.system}", fallbackFactory = RemoteUserFallbackFactory.class)
public interface RemoteUserService extends RemoteSelectService<SysUserDto> {

    /**
     * 新增用户
     *
     * @param user         用户对象
     * @return 结果
     */
    @PostMapping(headers = SecurityConstants.FROM_SOURCE_INNER)
    R<SysUserDto> addInner(@RequestBody SysUserDto user);
}