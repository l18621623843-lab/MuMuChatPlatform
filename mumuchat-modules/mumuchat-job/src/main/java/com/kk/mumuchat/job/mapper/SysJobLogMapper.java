package com.kk.mumuchat.job.mapper;

import com.kk.mumuchat.common.datasource.annotation.Isolate;
import com.kk.mumuchat.common.web.entity.mapper.BaseMapper;
import com.kk.mumuchat.job.api.domain.dto.SysJobLogDto;
import com.kk.mumuchat.job.api.domain.po.SysJobLogPo;
import com.kk.mumuchat.job.api.domain.query.SysJobLogQuery;


/**
 * 调度日志管理 数据层
 *
 * @author xueyi
 */
@Isolate
public interface SysJobLogMapper extends BaseMapper<SysJobLogQuery, SysJobLogDto, SysJobLogPo> {
}
