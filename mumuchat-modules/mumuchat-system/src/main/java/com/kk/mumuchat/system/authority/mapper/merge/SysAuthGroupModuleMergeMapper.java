package com.kk.mumuchat.system.authority.mapper.merge;

import com.kk.mumuchat.common.datasource.annotation.Master;
import com.kk.mumuchat.common.web.entity.mapper.BasicMapper;
import com.kk.mumuchat.system.authority.domain.merge.SysAuthGroupModuleMerge;

/**
 * 系统服务|权限模块|企业权限组-模块关联 数据层
 *
 * @author mumuchat
 */
@Master
public interface SysAuthGroupModuleMergeMapper extends BasicMapper<SysAuthGroupModuleMerge> {
}