package com.kk.mumuchat.system.organize.manager;

import com.kk.mumuchat.common.web.entity.manager.IBaseManager;
import com.kk.mumuchat.system.api.organize.domain.dto.SysEnterpriseDto;
import com.kk.mumuchat.system.api.organize.domain.query.SysEnterpriseQuery;

/**
 * 系统服务|组织模块|企业管理 数据封装层
 *
 * @author mumuchat
 */
public interface ISysEnterpriseManager extends IBaseManager<SysEnterpriseQuery, SysEnterpriseDto> {

    /**
     * 根据名称查询状态正常企业对象
     *
     * @param name 名称
     * @return 企业对象
     */
    SysEnterpriseDto selectByName(String name);
}
