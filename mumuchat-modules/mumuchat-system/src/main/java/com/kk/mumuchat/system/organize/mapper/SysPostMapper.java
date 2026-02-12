package com.kk.mumuchat.system.organize.mapper;

import com.kk.mumuchat.common.datasource.annotation.Isolate;
import com.kk.mumuchat.common.web.entity.mapper.BaseMapper;
import com.kk.mumuchat.system.api.organize.domain.dto.SysPostDto;
import com.kk.mumuchat.system.api.organize.domain.po.SysPostPo;
import com.kk.mumuchat.system.api.organize.domain.query.SysPostQuery;

/**
 * 系统服务|组织模块|岗位管理 数据层
 *
 * @author xueyi
 */
@Isolate
public interface SysPostMapper extends BaseMapper<SysPostQuery, SysPostDto, SysPostPo> {
}
