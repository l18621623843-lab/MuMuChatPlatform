package com.kk.mumuchat.system.file.mapper;

import com.kk.mumuchat.common.datasource.annotation.Isolate;
import com.kk.mumuchat.common.web.entity.mapper.BaseMapper;
import com.kk.mumuchat.system.file.domain.dto.SysFileDto;
import com.kk.mumuchat.system.file.domain.po.SysFilePo;
import com.kk.mumuchat.system.file.domain.query.SysFileQuery;

/**
 * 系统服务|素材模块|文件管理 数据层
 *
 * @author mumuchat
 */
@Isolate
public interface SysFileMapper extends BaseMapper<SysFileQuery, SysFileDto, SysFilePo> {
}