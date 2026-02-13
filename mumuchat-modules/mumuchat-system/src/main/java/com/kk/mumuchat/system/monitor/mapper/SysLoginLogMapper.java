package com.kk.mumuchat.system.monitor.mapper;

import com.kk.mumuchat.common.datasource.annotation.Isolate;
import com.kk.mumuchat.common.web.entity.mapper.BaseMapper;
import com.kk.mumuchat.system.api.log.domain.dto.SysLoginLogDto;
import com.kk.mumuchat.system.api.log.domain.po.SysLoginLogPo;
import com.kk.mumuchat.system.api.log.domain.query.SysLoginLogQuery;

/**
 * 系统服务|监控模块|访问日志管理 数据层
 *
 * @author mumuchat
 */
@Isolate
public interface SysLoginLogMapper extends BaseMapper<SysLoginLogQuery, SysLoginLogDto, SysLoginLogPo> {
}
