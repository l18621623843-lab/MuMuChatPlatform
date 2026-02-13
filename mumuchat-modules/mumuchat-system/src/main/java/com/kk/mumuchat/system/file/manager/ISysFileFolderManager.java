package com.kk.mumuchat.system.file.manager;

import com.kk.mumuchat.common.web.entity.manager.ITreeManager;
import com.kk.mumuchat.system.file.domain.dto.SysFileFolderDto;
import com.kk.mumuchat.system.file.domain.query.SysFileFolderQuery;

/**
 * 系统服务|素材模块|文件分类管理 数据封装层
 *
 * @author mumuchat
 */
public interface ISysFileFolderManager extends ITreeManager<SysFileFolderQuery, SysFileFolderDto> {
}