package com.kk.mumuchat.system.authority.mapper;

import com.kk.mumuchat.common.datasource.annotation.Master;
import com.kk.mumuchat.common.web.entity.mapper.BaseMapper;
import com.kk.mumuchat.system.authority.domain.dto.SysAuthGroupDto;
import com.kk.mumuchat.system.authority.domain.po.SysAuthGroupPo;
import com.kk.mumuchat.system.authority.domain.query.SysAuthGroupQuery;

/**
 * 系统服务|权限模块|企业权限组管理 数据层
 *
 * @author mumuchat
 */
@Master
public interface SysAuthGroupMapper extends BaseMapper<SysAuthGroupQuery, SysAuthGroupDto, SysAuthGroupPo> {
}