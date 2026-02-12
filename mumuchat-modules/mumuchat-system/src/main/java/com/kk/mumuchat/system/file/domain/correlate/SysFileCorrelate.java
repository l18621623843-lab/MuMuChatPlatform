package com.kk.mumuchat.system.file.domain.correlate;

import com.kk.mumuchat.common.web.correlate.domain.BaseCorrelate;
import com.kk.mumuchat.common.web.correlate.service.CorrelateService;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * 系统服务|素材模块|文件 关联映射
 */
@Getter
@AllArgsConstructor
public enum SysFileCorrelate implements CorrelateService {

    ;

    private final String info;
    private final List<? extends BaseCorrelate<?>> correlates;

}