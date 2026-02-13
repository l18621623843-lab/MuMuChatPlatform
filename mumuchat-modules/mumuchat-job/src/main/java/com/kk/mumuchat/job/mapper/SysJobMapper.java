package com.kk.mumuchat.job.mapper;

import com.kk.mumuchat.common.datasource.annotation.Master;
import com.kk.mumuchat.common.web.entity.mapper.BaseMapper;
import com.kk.mumuchat.job.api.domain.dto.SysJobDto;
import com.kk.mumuchat.job.api.domain.po.SysJobPo;
import com.kk.mumuchat.job.api.domain.query.SysJobQuery;

/**
 * 调度任务管理 数据层
 *
 * @author mumuchat
 */
@Master
public interface SysJobMapper extends BaseMapper<SysJobQuery, SysJobDto, SysJobPo> {
}
