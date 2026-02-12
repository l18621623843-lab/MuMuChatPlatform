package com.kk.mumuchat.tenant.source.manager.impl;

import com.kk.mumuchat.common.web.entity.manager.impl.BaseManagerImpl;
import com.kk.mumuchat.tenant.api.source.domain.dto.TeSourceDto;
import com.kk.mumuchat.tenant.api.source.domain.po.TeSourcePo;
import com.kk.mumuchat.tenant.api.source.domain.query.TeSourceQuery;
import com.kk.mumuchat.tenant.source.domain.model.TeSourceConverter;
import com.kk.mumuchat.tenant.source.manager.ITeSourceManager;
import com.kk.mumuchat.tenant.source.mapper.TeSourceMapper;
import org.springframework.stereotype.Component;

/**
 * 租户服务 | 策略模块 | 数据源管理 数据封装层处理
 *
 * @author xueyi
 */
@Component
public class TeSourceManagerImpl extends BaseManagerImpl<TeSourceQuery, TeSourceDto, TeSourcePo, TeSourceMapper, TeSourceConverter> implements ITeSourceManager {
}