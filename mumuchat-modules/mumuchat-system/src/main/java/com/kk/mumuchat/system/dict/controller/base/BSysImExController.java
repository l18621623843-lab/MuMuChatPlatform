package com.kk.mumuchat.system.dict.controller.base;

import com.kk.mumuchat.common.web.entity.controller.BaseController;
import com.kk.mumuchat.system.api.dict.domain.dto.SysImExDto;
import com.kk.mumuchat.system.api.dict.domain.query.SysImExQuery;
import com.kk.mumuchat.system.dict.service.ISysImExService;

/**
 * 导入导出配置管理|通用 业务处理
 *
 * @author xueyi
 */
public class BSysImExController extends BaseController<SysImExQuery, SysImExDto, ISysImExService> {

    /** 定义节点名称 */
    @Override
    protected String getNodeName() {
        return "导入导出配置" ;
    }
}
