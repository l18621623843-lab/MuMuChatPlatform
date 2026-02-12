package com.kk.mumuchat.tenant.tenant.mapper;

import com.kk.mumuchat.common.datasource.annotation.Master;
import com.kk.mumuchat.common.web.entity.mapper.BaseMapper;
import com.kk.mumuchat.tenant.api.tenant.domain.dto.TeTenantDto;
import com.kk.mumuchat.tenant.api.tenant.domain.po.TeTenantPo;
import com.kk.mumuchat.tenant.api.tenant.domain.query.TeTenantQuery;

/**
 * 租户服务 | 租户模块 | 租户管理 数据层
 *
 * @author xueyi
 */
@Master
public interface TeTenantMapper extends BaseMapper<TeTenantQuery, TeTenantDto, TeTenantPo> {
}