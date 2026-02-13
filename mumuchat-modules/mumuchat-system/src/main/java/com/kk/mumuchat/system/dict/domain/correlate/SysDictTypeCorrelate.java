package com.kk.mumuchat.system.dict.domain.correlate;

import com.kk.mumuchat.common.web.correlate.domain.BaseCorrelate;
import com.kk.mumuchat.common.web.correlate.domain.Direct;
import com.kk.mumuchat.common.web.correlate.service.CorrelateService;
import com.kk.mumuchat.system.api.dict.domain.dto.SysDictDataDto;
import com.kk.mumuchat.system.api.dict.domain.dto.SysDictTypeDto;
import com.kk.mumuchat.system.api.organize.domain.dto.SysEnterpriseDto;
import com.kk.mumuchat.system.dict.service.ISysDictDataService;
import com.kk.mumuchat.system.organize.service.ISysEnterpriseService;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static com.kk.mumuchat.common.web.correlate.contant.CorrelateConstants.SubOperate.DELETE;
import static com.kk.mumuchat.common.web.correlate.contant.CorrelateConstants.SubOperate.SELECT;

/**
 * 系统服务|字典模块|字典类型 关联映射
 *
 * @author mumuchat
 */
@Getter
@AllArgsConstructor
public enum SysDictTypeCorrelate implements CorrelateService {

    EN_INFO_SELECT("企业查询|（企业信息）", new ArrayList<>() {{
        // 字典类型|企业信息
        add(new Direct<>(SELECT, ISysEnterpriseService.class, SysDictTypeDto::getTenantId, SysEnterpriseDto::getId, SysDictTypeDto::getEnterpriseInfo));
    }}),
    CACHE_REFRESH("缓存|（字典数据）", new ArrayList<>() {{
        // 字典类型|字典数据
        add(new Direct<>(SELECT, ISysDictDataService.class, SysDictTypeDto::getCode, SysDictDataDto::getCode, SysDictTypeDto::getSubList));
    }}),
    BASE_DEL("默认删除|（字典数据）", new ArrayList<>() {{
        // 字典类型|字典数据
        add(new Direct<>(DELETE, ISysDictDataService.class, SysDictTypeDto::getCode, SysDictDataDto::getCode));
    }});

    private final String info;
    private final List<? extends BaseCorrelate<?>> correlates;

}
