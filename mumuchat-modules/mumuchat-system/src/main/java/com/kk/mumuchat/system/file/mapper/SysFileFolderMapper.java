package com.kk.mumuchat.system.file.mapper;

import com.kk.mumuchat.common.datasource.annotation.Isolate;
import com.kk.mumuchat.common.web.entity.mapper.TreeMapper;
import com.kk.mumuchat.system.file.domain.dto.SysFileFolderDto;
import com.kk.mumuchat.system.file.domain.po.SysFileFolderPo;
import com.kk.mumuchat.system.file.domain.query.SysFileFolderQuery;

/**
 * 系统服务|素材模块|文件分类管理 数据层
 *
 * @author xueyi
 */
@Isolate
public interface SysFileFolderMapper extends TreeMapper<SysFileFolderQuery, SysFileFolderDto, SysFileFolderPo> {
}