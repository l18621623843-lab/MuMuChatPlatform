package com.kk.mumuchat.system.authority.mapper;

import com.kk.mumuchat.common.datasource.annotation.Master;
import com.kk.mumuchat.common.web.entity.mapper.TreeMapper;
import com.kk.mumuchat.system.api.authority.domain.dto.SysMenuDto;
import com.kk.mumuchat.system.api.authority.domain.po.SysMenuPo;
import com.kk.mumuchat.system.api.authority.domain.query.SysMenuQuery;

/**
 * 系统服务|权限模块|菜单管理 数据层
 *
 * @author xueyi
 */
@Master
public interface SysMenuMapper extends TreeMapper<SysMenuQuery, SysMenuDto, SysMenuPo> {
}
