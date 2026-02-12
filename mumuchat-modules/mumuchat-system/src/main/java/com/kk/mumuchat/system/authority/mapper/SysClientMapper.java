package com.kk.mumuchat.system.authority.mapper;

import com.kk.mumuchat.common.datasource.annotation.Master;
import com.kk.mumuchat.common.web.entity.mapper.BaseMapper;
import com.kk.mumuchat.system.api.authority.domain.dto.SysClientDto;
import com.kk.mumuchat.system.api.authority.domain.po.SysClientPo;
import com.kk.mumuchat.system.api.authority.domain.query.SysClientQuery;

/**
 * 系统服务|权限模块|客户端管理 数据层
 *
 * @author xueyi
 */
@Master
public interface SysClientMapper extends BaseMapper<SysClientQuery, SysClientDto, SysClientPo> {
}
