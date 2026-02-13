package com.kk.mumuchat.system.organize.mapper;

import com.kk.mumuchat.common.datasource.annotation.Isolate;
import com.kk.mumuchat.common.web.entity.mapper.TreeMapper;
import com.kk.mumuchat.system.api.organize.domain.dto.SysDeptDto;
import com.kk.mumuchat.system.api.organize.domain.po.SysDeptPo;
import com.kk.mumuchat.system.api.organize.domain.query.SysDeptQuery;

/**
 * 系统服务|组织模块|部门管理 数据层
 *
 * @author mumuchat
 */
@Isolate
public interface SysDeptMapper extends TreeMapper<SysDeptQuery, SysDeptDto, SysDeptPo> {
}
