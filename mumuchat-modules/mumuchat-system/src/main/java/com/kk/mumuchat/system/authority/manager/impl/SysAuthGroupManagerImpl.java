package com.kk.mumuchat.system.authority.manager.impl;

import com.kk.mumuchat.common.web.entity.manager.impl.BaseManagerImpl;
import com.kk.mumuchat.system.authority.domain.dto.SysAuthGroupDto;
import com.kk.mumuchat.system.authority.domain.model.SysAuthGroupConverter;
import com.kk.mumuchat.system.authority.domain.po.SysAuthGroupPo;
import com.kk.mumuchat.system.authority.domain.query.SysAuthGroupQuery;
import com.kk.mumuchat.system.authority.manager.ISysAuthGroupManager;
import com.kk.mumuchat.system.authority.mapper.SysAuthGroupMapper;
import org.springframework.stereotype.Component;

/**
 * 系统服务|权限模块|企业权限组管理 数据封装层处理
 *
 * @author mumuchat
 */
@Component
public class SysAuthGroupManagerImpl extends BaseManagerImpl<SysAuthGroupQuery, SysAuthGroupDto, SysAuthGroupPo, SysAuthGroupMapper, SysAuthGroupConverter> implements ISysAuthGroupManager {
}