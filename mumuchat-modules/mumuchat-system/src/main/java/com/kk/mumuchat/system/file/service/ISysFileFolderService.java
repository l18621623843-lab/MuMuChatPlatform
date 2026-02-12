package com.kk.mumuchat.system.file.service;

import com.kk.mumuchat.common.web.entity.service.ITreeService;
import com.kk.mumuchat.system.file.domain.dto.SysFileFolderDto;
import com.kk.mumuchat.system.file.domain.query.SysFileFolderQuery;

/**
 * 系统服务|素材模块|文件分类管理 服务层
 *
 * @author xueyi
 */
public interface ISysFileFolderService extends ITreeService<SysFileFolderQuery, SysFileFolderDto> {
}