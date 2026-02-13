package com.kk.mumuchat.system.file.service.impl;

import com.kk.mumuchat.common.web.entity.service.impl.BaseServiceImpl;
import com.kk.mumuchat.system.file.domain.correlate.SysFileCorrelate;
import com.kk.mumuchat.system.file.domain.dto.SysFileDto;
import com.kk.mumuchat.system.file.domain.query.SysFileQuery;
import com.kk.mumuchat.system.file.manager.ISysFileManager;
import com.kk.mumuchat.system.file.service.ISysFileService;
import org.springframework.stereotype.Service;

/**
 * 系统服务|素材模块|文件管理 服务层处理
 *
 * @author mumuchat
 */
@Service
public class SysFileServiceImpl extends BaseServiceImpl<SysFileQuery, SysFileDto, SysFileCorrelate, ISysFileManager> implements ISysFileService {

    /**
     * 根据文件Url删除文件
     *
     * @param url 文件路径
     * @return 结果
     */
    @Override
    public int deleteByUrl(String url) {
        return baseManager.deleteByUrl(url);
    }
}