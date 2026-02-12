package com.kk.mumuchat.system.dict.mapper;

import com.kk.mumuchat.common.datasource.annotation.Master;
import com.kk.mumuchat.common.web.entity.mapper.BaseMapper;
import com.kk.mumuchat.system.api.dict.domain.dto.SysConfigDto;
import com.kk.mumuchat.system.api.dict.domain.po.SysConfigPo;
import com.kk.mumuchat.system.api.dict.domain.query.SysConfigQuery;

/**
 * 系统服务|字典模块|参数管理 数据层
 *
 * @author xueyi
 */
@Master
public interface SysConfigMapper extends BaseMapper<SysConfigQuery, SysConfigDto, SysConfigPo> {
}