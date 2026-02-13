package com.kk.mumuchat.system.dict.mapper;

import com.kk.mumuchat.common.datasource.annotation.Master;
import com.kk.mumuchat.common.web.entity.mapper.BaseMapper;
import com.kk.mumuchat.system.api.dict.domain.dto.SysDictTypeDto;
import com.kk.mumuchat.system.api.dict.domain.po.SysDictTypePo;
import com.kk.mumuchat.system.api.dict.domain.query.SysDictTypeQuery;

/**
 * 系统服务|字典模块|字典类型管理 数据层
 *
 * @author mumuchat
 */
@Master
public interface SysDictTypeMapper extends BaseMapper<SysDictTypeQuery, SysDictTypeDto, SysDictTypePo> {
}
