package com.kk.mumuchat.system.file.controller.base;

import com.kk.mumuchat.common.web.entity.controller.BaseController;
import com.kk.mumuchat.system.file.domain.dto.SysFileDto;
import com.kk.mumuchat.system.file.domain.query.SysFileQuery;
import com.kk.mumuchat.system.file.service.ISysFileService;

/**
 * 系统服务|素材模块|文件管理|通用 业务处理
 *
 * @author mumuchat
 */
public class BSysFileController extends BaseController<SysFileQuery, SysFileDto, ISysFileService> {

    /** 定义节点名称 */
    @Override
    protected String getNodeName() {
        return "菜单文件" ;
    }
}
