package com.kk.mumuchat.system.api.organize.feign;

import com.kk.mumuchat.common.core.constant.basic.SecurityConstants;
import com.kk.mumuchat.common.core.web.feign.RemoteSelectService;
import com.kk.mumuchat.common.core.web.result.R;
import com.kk.mumuchat.system.api.organize.domain.dto.SysDeptDto;
import com.kk.mumuchat.system.api.organize.feign.factory.RemoteDeptFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 系统服务|组织模块|部门服务
 *
 * @author xueyi
 */
@FeignClient(contextId = "remoteDeptService", path = "/inner/dept", value = "${xueyi.remote.service.system}", fallbackFactory = RemoteDeptFallbackFactory.class)
public interface RemoteDeptService extends RemoteSelectService<SysDeptDto> {

    /**
     * 新增部门
     *
     * @param dept         部门对象
     * @return 结果
     */
    @PostMapping(headers = SecurityConstants.FROM_SOURCE_INNER)
    R<SysDeptDto> addInner(@RequestBody SysDeptDto dept);

}