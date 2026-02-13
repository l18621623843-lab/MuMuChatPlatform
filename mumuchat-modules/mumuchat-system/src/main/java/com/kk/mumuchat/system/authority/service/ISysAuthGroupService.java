package com.kk.mumuchat.system.authority.service;

import com.kk.mumuchat.common.web.entity.service.IBaseService;
import com.kk.mumuchat.system.authority.domain.dto.SysAuthGroupDto;
import com.kk.mumuchat.system.authority.domain.query.SysAuthGroupQuery;

import java.io.Serializable;

/**
 * 系统服务|权限模块|企业权限组管理 服务层
 *
 * @author mumuchat
 */
public interface ISysAuthGroupService extends IBaseService<SysAuthGroupQuery, SysAuthGroupDto> {

    /**
     * 根据Id查询单条数据对象
     *
     * @param id Id
     * @return 数据对象
     */
    SysAuthGroupDto selectInfoById(Serializable id);

}