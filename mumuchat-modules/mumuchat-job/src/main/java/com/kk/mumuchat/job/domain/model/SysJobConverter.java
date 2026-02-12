package com.kk.mumuchat.job.domain.model;

import com.kk.mumuchat.common.core.web.entity.model.BaseConverter;
import com.kk.mumuchat.job.api.domain.dto.SysJobDto;
import com.kk.mumuchat.job.api.domain.po.SysJobPo;
import com.kk.mumuchat.job.api.domain.query.SysJobQuery;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * 调度任务 对象映射器
 *
 * @author xueyi
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SysJobConverter extends BaseConverter<SysJobQuery, SysJobDto, SysJobPo> {
}
