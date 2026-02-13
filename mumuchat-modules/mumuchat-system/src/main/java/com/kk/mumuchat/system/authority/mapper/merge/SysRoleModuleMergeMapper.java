package com.kk.mumuchat.system.authority.mapper.merge;

import com.kk.mumuchat.common.datasource.annotation.Isolate;
import com.kk.mumuchat.common.web.entity.mapper.BasicMapper;
import com.kk.mumuchat.system.authority.domain.merge.SysRoleModuleMerge;

/**
 * 系统服务|权限模块|角色-模块关联 数据层
 *
 * @author mumuchat
 */
@Isolate
public interface SysRoleModuleMergeMapper extends BasicMapper<SysRoleModuleMerge> {
}
