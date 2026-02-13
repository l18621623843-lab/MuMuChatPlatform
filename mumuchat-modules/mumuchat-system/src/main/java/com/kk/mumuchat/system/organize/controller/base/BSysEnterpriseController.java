package com.kk.mumuchat.system.organize.controller.base;

import com.kk.mumuchat.common.web.entity.controller.BaseController;
import com.kk.mumuchat.system.api.organize.domain.dto.SysEnterpriseDto;
import com.kk.mumuchat.system.api.organize.domain.query.SysEnterpriseQuery;
import com.kk.mumuchat.system.organize.service.ISysEnterpriseService;

/**
 * 系统服务|组织模块|企业管理|通用 业务处理
 *
 * @author mumuchat
 */
public class BSysEnterpriseController extends BaseController<SysEnterpriseQuery, SysEnterpriseDto, ISysEnterpriseService> {

    /** 定义节点名称 */
    protected String getNodeName() {
        return "企业";
    }

}
