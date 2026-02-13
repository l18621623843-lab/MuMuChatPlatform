package com.kk.mumuchat.system.file.manager.impl;

import com.kk.mumuchat.common.web.entity.manager.impl.TreeManagerImpl;
import com.kk.mumuchat.system.file.domain.dto.SysFileFolderDto;
import com.kk.mumuchat.system.file.domain.model.SysFileFolderConverter;
import com.kk.mumuchat.system.file.domain.po.SysFileFolderPo;
import com.kk.mumuchat.system.file.domain.query.SysFileFolderQuery;
import com.kk.mumuchat.system.file.manager.ISysFileFolderManager;
import com.kk.mumuchat.system.file.mapper.SysFileFolderMapper;
import org.springframework.stereotype.Component;

/**
 * 系统服务|素材模块|文件分类管理 数据封装层处理
 *
 * @author mumuchat
 */
@Component
public class SysFileFolderManagerImpl extends TreeManagerImpl<SysFileFolderQuery, SysFileFolderDto, SysFileFolderPo, SysFileFolderMapper, SysFileFolderConverter> implements ISysFileFolderManager {
}