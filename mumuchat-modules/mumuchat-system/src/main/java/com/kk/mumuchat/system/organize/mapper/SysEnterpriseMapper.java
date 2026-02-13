package com.kk.mumuchat.system.organize.mapper;

import com.kk.mumuchat.common.datasource.annotation.Master;
import com.kk.mumuchat.common.web.entity.mapper.BaseMapper;
import com.kk.mumuchat.system.api.organize.domain.dto.SysEnterpriseDto;
import com.kk.mumuchat.system.api.organize.domain.po.SysEnterprisePo;
import com.kk.mumuchat.system.api.organize.domain.query.SysEnterpriseQuery;

/**
 * 系统服务|组织模块|企业管理 数据层
 *
 * @author mumuchat
 */
@Master
public interface SysEnterpriseMapper extends BaseMapper<SysEnterpriseQuery, SysEnterpriseDto, SysEnterprisePo> {
}
