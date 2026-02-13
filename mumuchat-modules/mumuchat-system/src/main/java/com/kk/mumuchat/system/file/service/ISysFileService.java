package com.kk.mumuchat.system.file.service;

import com.kk.mumuchat.common.web.entity.service.IBaseService;
import com.kk.mumuchat.system.file.domain.dto.SysFileDto;
import com.kk.mumuchat.system.file.domain.query.SysFileQuery;

/**
 * 系统服务|素材模块|文件管理 服务层
 *
 * @author mumuchat
 */
public interface ISysFileService extends IBaseService<SysFileQuery, SysFileDto> {

    /**
     * 根据文件Url删除文件
     *
     * @param url 文件路径
     * @return 结果
     */
    int deleteByUrl(String url);
}