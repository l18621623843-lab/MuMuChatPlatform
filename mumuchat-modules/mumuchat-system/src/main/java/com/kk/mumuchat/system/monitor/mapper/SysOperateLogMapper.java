package com.kk.mumuchat.system.monitor.mapper;

import com.kk.mumuchat.common.datasource.annotation.Isolate;
import com.kk.mumuchat.common.web.entity.mapper.BaseMapper;
import com.kk.mumuchat.system.api.log.domain.dto.SysOperateLogDto;
import com.kk.mumuchat.system.api.log.domain.po.SysOperateLogPo;
import com.kk.mumuchat.system.api.log.domain.query.SysOperateLogQuery;

/**
 * 系统服务|监控模块|操作日志管理 数据层
 *
 * @author mumuchat
 */
@Isolate
public interface SysOperateLogMapper extends BaseMapper<SysOperateLogQuery, SysOperateLogDto, SysOperateLogPo> {
}
