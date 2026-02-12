package com.kk.mumuchat.system.organize.mapper.merge;

import com.kk.mumuchat.common.datasource.annotation.Isolate;
import com.kk.mumuchat.common.web.entity.mapper.BasicMapper;
import com.kk.mumuchat.system.organize.domain.merge.SysRolePostMerge;

/**
 * 系统服务|组织模块|角色-岗位关联（权限范围） 数据层
 *
 * @author xueyi
 */
@Isolate
public interface SysRolePostMergeMapper extends BasicMapper<SysRolePostMerge> {
}