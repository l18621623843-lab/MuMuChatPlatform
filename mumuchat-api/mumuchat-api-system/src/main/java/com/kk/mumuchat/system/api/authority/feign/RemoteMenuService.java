package com.kk.mumuchat.system.api.authority.feign;

import com.kk.mumuchat.common.core.constant.basic.SecurityConstants;
import com.kk.mumuchat.common.core.web.result.R;
import com.kk.mumuchat.system.api.authority.domain.dto.SysMenuDto;
import com.kk.mumuchat.system.api.authority.feign.factory.RemoteMenuFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 系统服务|权限模块|菜单服务
 *
 * @author xueyi
 */
@FeignClient(contextId = "remoteMenuService", path = "/inner/menu", value = "${xueyi.remote.service.system}", fallbackFactory = RemoteMenuFallbackFactory.class)
public interface RemoteMenuService {

    /**
     * 根据Id获取菜单信息
     *
     * @param id 菜单Id
     * @return 菜单对象
     */
    @GetMapping(value = "/id", headers = SecurityConstants.FROM_SOURCE_INNER)
    R<SysMenuDto> getInfoInner(@RequestParam("id") Long id);
}