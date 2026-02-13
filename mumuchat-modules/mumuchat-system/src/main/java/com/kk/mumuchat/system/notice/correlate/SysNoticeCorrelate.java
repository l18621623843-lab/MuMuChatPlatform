package com.kk.mumuchat.system.notice.correlate;

import com.kk.mumuchat.common.web.correlate.domain.BaseCorrelate;
import com.kk.mumuchat.common.web.correlate.service.CorrelateService;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * 系统服务|消息模块|通知公告 关联映射
 *
 * @author mumuchat
 */
@Getter
@AllArgsConstructor
public enum SysNoticeCorrelate implements CorrelateService {

    ;

    private final String info;
    private final List<? extends BaseCorrelate<?>> correlates;

}
