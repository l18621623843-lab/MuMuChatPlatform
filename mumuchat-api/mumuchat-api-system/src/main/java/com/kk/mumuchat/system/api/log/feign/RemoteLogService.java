package com.kk.mumuchat.system.api.log.feign;

import com.kk.mumuchat.common.core.constant.basic.SecurityConstants;
import com.kk.mumuchat.common.core.web.result.R;
import com.kk.mumuchat.system.api.log.domain.dto.SysLoginLogDto;
import com.kk.mumuchat.system.api.log.domain.dto.SysOperateLogDto;
import com.kk.mumuchat.system.api.log.feign.factory.RemoteLogFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
/**
 * 系统服务|监控模块|日志服务
 *
 * @author mumuchat
 */
@FeignClient(contextId = "remoteLogService", value = "${xueyi.remote.service.system}", fallbackFactory = RemoteLogFallbackFactory.class)
public interface RemoteLogService {

    /**
     * 保存系统日志
     *
     * @param operateLog   日志实体
     * @return 结果
     */
    @PostMapping(value = "/inner/operateLog", headers = SecurityConstants.FROM_SOURCE_INNER)
    R<Boolean> saveOperateLog(@RequestBody SysOperateLogDto operateLog) throws Exception;

    /**
     * 保存访问记录
     *
     * @param loginInfo    访问实体
     * @param enterpriseId 企业Id
     * @param sourceName   数据源
     * @return 结果
     */
    @PostMapping(value = "/inner/loginLog", headers = SecurityConstants.FROM_SOURCE_INNER)
    R<Boolean> saveLoginInfo(@RequestBody SysLoginLogDto loginInfo, @RequestHeader(SecurityConstants.ENTERPRISE_ID) Long enterpriseId, @RequestHeader(SecurityConstants.SOURCE_NAME) String sourceName);
}