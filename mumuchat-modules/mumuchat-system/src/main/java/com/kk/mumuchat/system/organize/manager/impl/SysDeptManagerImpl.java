package com.kk.mumuchat.system.organize.manager.impl;

import com.kk.mumuchat.common.web.entity.manager.impl.TreeManagerImpl;
import com.kk.mumuchat.system.api.organize.domain.dto.SysDeptDto;
import com.kk.mumuchat.system.api.organize.domain.po.SysDeptPo;
import com.kk.mumuchat.system.api.organize.domain.query.SysDeptQuery;
import com.kk.mumuchat.system.organize.domain.model.SysDeptConverter;
import com.kk.mumuchat.system.organize.manager.ISysDeptManager;
import com.kk.mumuchat.system.organize.mapper.SysDeptMapper;
import org.springframework.stereotype.Component;

/**
 * 系统服务|组织模块|部门管理 数据封装层处理
 *
 * @author mumuchat
 */
@Component
public class SysDeptManagerImpl extends TreeManagerImpl<SysDeptQuery, SysDeptDto, SysDeptPo, SysDeptMapper, SysDeptConverter> implements ISysDeptManager {
}
