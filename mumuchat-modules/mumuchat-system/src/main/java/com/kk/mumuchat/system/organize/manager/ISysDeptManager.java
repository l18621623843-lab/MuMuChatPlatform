package com.kk.mumuchat.system.organize.manager;

import com.kk.mumuchat.common.web.entity.manager.ITreeManager;
import com.kk.mumuchat.system.api.organize.domain.dto.SysDeptDto;
import com.kk.mumuchat.system.api.organize.domain.query.SysDeptQuery;

/**
 * 系统服务|组织模块|部门管理 数据封装层
 *
 * @author mumuchat
 */
public interface ISysDeptManager extends ITreeManager<SysDeptQuery, SysDeptDto> {
}
