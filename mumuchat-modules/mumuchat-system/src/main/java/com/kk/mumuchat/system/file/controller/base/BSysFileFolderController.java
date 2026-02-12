package com.kk.mumuchat.system.file.controller.base;

import com.kk.mumuchat.common.web.entity.controller.TreeController;
import com.kk.mumuchat.system.file.domain.dto.SysFileFolderDto;
import com.kk.mumuchat.system.file.domain.query.SysFileFolderQuery;
import com.kk.mumuchat.system.file.service.ISysFileFolderService;

/**
 * 系统服务|素材模块|文件分类管理|通用 业务处理
 *
 * @author xueyi
 */
public class BSysFileFolderController extends TreeController<SysFileFolderQuery, SysFileFolderDto, ISysFileFolderService> {

    /** 定义节点名称 */
    @Override
    protected String getNodeName() {
        return "菜单文件分类" ;
    }
}
