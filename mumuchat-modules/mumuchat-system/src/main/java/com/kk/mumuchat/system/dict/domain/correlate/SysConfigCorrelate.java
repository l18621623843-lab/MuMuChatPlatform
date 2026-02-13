package com.kk.mumuchat.system.dict.domain.correlate;

import com.kk.mumuchat.common.web.correlate.domain.BaseCorrelate;
import com.kk.mumuchat.common.web.correlate.domain.Direct;
import com.kk.mumuchat.common.web.correlate.service.CorrelateService;
import com.kk.mumuchat.system.api.dict.domain.dto.SysConfigDto;
import com.kk.mumuchat.system.api.organize.domain.dto.SysEnterpriseDto;
import com.kk.mumuchat.system.organize.service.ISysEnterpriseService;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static com.kk.mumuchat.common.web.correlate.contant.CorrelateConstants.SubOperate.SELECT;

/**
 * 系统服务|字典模块|参数 关联映射
 *
 * @author mumuchat
 */
@Getter
@AllArgsConstructor
public enum SysConfigCorrelate implements CorrelateService {

    EN_INFO_SELECT("企业查询|（企业信息）", new ArrayList<>() {{
        // 字典类型|企业信息
        add(new Direct<>(SELECT, ISysEnterpriseService.class, SysConfigDto::getTenantId, SysEnterpriseDto::getId, SysConfigDto::getEnterpriseInfo));
    }});

    private final String info;
    private final List<? extends BaseCorrelate<?>> correlates;

}
