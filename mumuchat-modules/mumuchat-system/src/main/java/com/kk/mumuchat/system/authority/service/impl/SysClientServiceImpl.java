package com.kk.mumuchat.system.authority.service.impl;

import com.kk.mumuchat.common.web.entity.service.impl.BaseServiceImpl;
import com.kk.mumuchat.system.api.authority.domain.dto.SysClientDto;
import com.kk.mumuchat.system.api.authority.domain.query.SysClientQuery;
import com.kk.mumuchat.system.authority.domain.correlate.SysClientCorrelate;
import com.kk.mumuchat.system.authority.manager.ISysClientManager;
import com.kk.mumuchat.system.authority.service.ISysClientService;
import org.springframework.stereotype.Service;

/**
 * 系统服务|权限模块|客户端管理 服务层处理
 *
 * @author mumuchat
 */
@Service
public class SysClientServiceImpl extends BaseServiceImpl<SysClientQuery, SysClientDto, SysClientCorrelate, ISysClientManager> implements ISysClientService {

    /**
     * 根据客户端Id查询客户端信息
     *
     * @param clientId 客户端Id
     * @return 客户端对象
     */
    @Override
    public SysClientDto selectByClientId(String clientId) {
        return baseManager.selectByClientId(clientId);
    }
}
