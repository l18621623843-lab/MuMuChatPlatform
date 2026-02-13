package com.kk.mumuchat.system.file.domain.model;

import com.kk.mumuchat.common.core.web.entity.model.TreeConverter;
import com.kk.mumuchat.system.file.domain.dto.SysFileFolderDto;
import com.kk.mumuchat.system.file.domain.po.SysFileFolderPo;
import com.kk.mumuchat.system.file.domain.query.SysFileFolderQuery;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * 系统服务|素材模块|文件分类 对象映射器
 *
 * @author mumuchat
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SysFileFolderConverter extends TreeConverter<SysFileFolderQuery, SysFileFolderDto, SysFileFolderPo> {
}
