package com.kk.mumuchat.system.dict.domain.correlate;

import com.kk.mumuchat.common.web.correlate.domain.BaseCorrelate;
import com.kk.mumuchat.common.web.correlate.service.CorrelateService;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * 系统服务|字典模块|字典数据 关联映射
 *
 * @author mumuchat
 */
@Getter
@AllArgsConstructor
public enum SysDictDataCorrelate implements CorrelateService {

    ;

    private final String info;
    private final List<? extends BaseCorrelate<?>> correlates;

}
