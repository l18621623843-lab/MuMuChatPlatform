package com.kk.mumuchat.system.monitor.domain.correlate;

import com.kk.mumuchat.common.web.correlate.domain.BaseCorrelate;
import com.kk.mumuchat.common.web.correlate.service.CorrelateService;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * 系统服务|监控模块|访问日志 关联映射
 *
 * @author mumuchat
 */
@Getter
@AllArgsConstructor
public enum SysLoginLogCorrelate implements CorrelateService {

    ;

    private final String info;
    private final List<? extends BaseCorrelate<?>> correlates;

}
