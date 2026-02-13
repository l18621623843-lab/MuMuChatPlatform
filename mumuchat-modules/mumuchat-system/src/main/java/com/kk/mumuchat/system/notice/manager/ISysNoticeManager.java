package com.kk.mumuchat.system.notice.manager;

import com.kk.mumuchat.common.web.entity.manager.IBaseManager;
import com.kk.mumuchat.system.notice.domain.dto.SysNoticeDto;
import com.kk.mumuchat.system.notice.domain.query.SysNoticeQuery;

/**
 * 系统服务|消息模块|通知公告管理 数据封装层
 *
 * @author mumuchat
 */
public interface ISysNoticeManager extends IBaseManager<SysNoticeQuery, SysNoticeDto> {
}
