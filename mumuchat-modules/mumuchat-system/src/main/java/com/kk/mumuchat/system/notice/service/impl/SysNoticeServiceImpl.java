package com.kk.mumuchat.system.notice.service.impl;

import com.kk.mumuchat.common.datascope.annotation.DataScope;
import com.kk.mumuchat.common.web.entity.service.impl.BaseServiceImpl;
import com.kk.mumuchat.system.notice.correlate.SysNoticeCorrelate;
import com.kk.mumuchat.system.notice.domain.dto.SysNoticeDto;
import com.kk.mumuchat.system.notice.domain.query.SysNoticeQuery;
import com.kk.mumuchat.system.notice.manager.ISysNoticeManager;
import com.kk.mumuchat.system.notice.service.ISysNoticeService;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.kk.mumuchat.common.core.constant.basic.SecurityConstants.CREATE_BY;

/**
 * 系统服务|消息模块|通知公告管理 服务层处理
 *
 * @author xueyi
 */
@Service
public class SysNoticeServiceImpl extends BaseServiceImpl<SysNoticeQuery, SysNoticeDto, SysNoticeCorrelate, ISysNoticeManager> implements ISysNoticeService {

    /**
     * 查询通知公告对象列表|数据权限
     *
     * @param notice 通知公告对象
     * @return 通知公告对象集合
     */
    @Override
    @DataScope(userAlias = CREATE_BY, mapperScope = {"SysNoticeMapper"})
    public List<SysNoticeDto> selectListScope(SysNoticeQuery notice) {
        return super.selectListScope(notice);
    }
}