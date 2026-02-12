package com.kk.mumuchat.system.dict.mapper;

import com.kk.mumuchat.common.datasource.annotation.Master;
import com.kk.mumuchat.common.web.entity.mapper.BaseMapper;
import com.kk.mumuchat.system.api.dict.domain.dto.SysImExDto;
import com.kk.mumuchat.system.api.dict.domain.po.SysImExPo;
import com.kk.mumuchat.system.api.dict.domain.query.SysImExQuery;

/**
 * 导入导出配置管理 数据层
 *
 * @author xueyi
 */
@Master
public interface SysImExMapper extends BaseMapper<SysImExQuery, SysImExDto, SysImExPo> {
}