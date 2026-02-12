package com.kk.mumuchat.system.file.service.impl;

import com.kk.mumuchat.common.web.entity.service.impl.TreeServiceImpl;
import com.kk.mumuchat.system.file.domain.correlate.SysFileFolderCorrelate;
import com.kk.mumuchat.system.file.domain.dto.SysFileFolderDto;
import com.kk.mumuchat.system.file.domain.query.SysFileFolderQuery;
import com.kk.mumuchat.system.file.manager.ISysFileFolderManager;
import com.kk.mumuchat.system.file.service.ISysFileFolderService;
import org.springframework.stereotype.Service;

/**
 * 系统服务|素材模块|文件分类管理 服务层处理
 *
 * @author xueyi
 */
@Service
public class SysFileFolderServiceImpl extends TreeServiceImpl<SysFileFolderQuery, SysFileFolderDto, SysFileFolderCorrelate, ISysFileFolderManager> implements ISysFileFolderService {
}