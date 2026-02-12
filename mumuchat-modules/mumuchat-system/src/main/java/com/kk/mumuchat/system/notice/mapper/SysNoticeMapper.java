package com.kk.mumuchat.system.notice.mapper;

import com.kk.mumuchat.common.datasource.annotation.Isolate;
import com.kk.mumuchat.common.web.entity.mapper.BaseMapper;
import com.kk.mumuchat.system.notice.domain.dto.SysNoticeDto;
import com.kk.mumuchat.system.notice.domain.po.SysNoticePo;
import com.kk.mumuchat.system.notice.domain.query.SysNoticeQuery;

/**
 * 系统服务|消息模块|通知公告管理 数据层
 *
 * @author xueyi
 */
@Isolate
public interface SysNoticeMapper extends BaseMapper<SysNoticeQuery, SysNoticeDto, SysNoticePo> {
}
