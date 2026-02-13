package com.kk.mumuchat.system.notice.manager.impl;

import com.kk.mumuchat.common.web.entity.manager.impl.BaseManagerImpl;
import com.kk.mumuchat.system.notice.domain.dto.SysNoticeDto;
import com.kk.mumuchat.system.notice.domain.model.SysNoticeConverter;
import com.kk.mumuchat.system.notice.domain.po.SysNoticePo;
import com.kk.mumuchat.system.notice.domain.query.SysNoticeQuery;
import com.kk.mumuchat.system.notice.manager.ISysNoticeManager;
import com.kk.mumuchat.system.notice.mapper.SysNoticeMapper;
import org.springframework.stereotype.Component;

/**
 * 系统服务|消息模块|通知公告管理 数据封装层处理
 *
 * @author mumuchat
 */
@Component
public class SysNoticeManagerImpl extends BaseManagerImpl<SysNoticeQuery, SysNoticeDto, SysNoticePo, SysNoticeMapper, SysNoticeConverter> implements ISysNoticeManager {
}
