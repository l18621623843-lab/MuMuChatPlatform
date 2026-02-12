package com.kk.mumuchat.system.dict.domain.model;

import com.kk.mumuchat.common.core.web.entity.model.BaseConverter;
import com.kk.mumuchat.system.api.dict.domain.dto.SysDictDataDto;
import com.kk.mumuchat.system.api.dict.domain.po.SysDictDataPo;
import com.kk.mumuchat.system.api.dict.domain.query.SysDictDataQuery;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * 系统服务|字典模块|字典数据 对象映射器
 *
 * @author xueyi
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SysDictDataConverter extends BaseConverter<SysDictDataQuery, SysDictDataDto, SysDictDataPo> {
}