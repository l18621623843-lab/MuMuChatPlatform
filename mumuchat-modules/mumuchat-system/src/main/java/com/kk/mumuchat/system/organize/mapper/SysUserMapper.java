package com.kk.mumuchat.system.organize.mapper;

import com.kk.mumuchat.common.datasource.annotation.Isolate;
import com.kk.mumuchat.common.web.entity.mapper.BaseMapper;
import com.kk.mumuchat.system.api.organize.domain.dto.SysUserDto;
import com.kk.mumuchat.system.api.organize.domain.po.SysUserPo;
import com.kk.mumuchat.system.api.organize.domain.query.SysUserQuery;

/**
 * 系统服务|组织模块|用户管理 数据层
 *
 * @author mumuchat
 */
@Isolate
public interface SysUserMapper extends BaseMapper<SysUserQuery, SysUserDto, SysUserPo> {
}
