package com.kk.mumuchat.system.authority.mapper;

import com.kk.mumuchat.common.datasource.annotation.Master;
import com.kk.mumuchat.common.web.entity.mapper.BaseMapper;
import com.kk.mumuchat.system.api.authority.domain.dto.SysModuleDto;
import com.kk.mumuchat.system.api.authority.domain.po.SysModulePo;
import com.kk.mumuchat.system.api.authority.domain.query.SysModuleQuery;

/**
 * 系统服务|权限模块|角色管理 数据层
 *
 * @author xueyi
 */
@Master
public interface SysModuleMapper extends BaseMapper<SysModuleQuery, SysModuleDto, SysModulePo> {
}
