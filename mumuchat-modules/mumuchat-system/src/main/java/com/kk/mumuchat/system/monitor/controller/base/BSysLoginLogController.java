package com.kk.mumuchat.system.monitor.controller.base;

import com.kk.mumuchat.common.web.entity.controller.BaseController;
import com.kk.mumuchat.system.api.log.domain.dto.SysLoginLogDto;
import com.kk.mumuchat.system.api.log.domain.query.SysLoginLogQuery;
import com.kk.mumuchat.system.monitor.service.ISysLoginLogService;

/**
 * 系统服务|监控模块|访问日志管理|通用 业务处理
 *
 * @author xueyi
 */
public class BSysLoginLogController extends BaseController<SysLoginLogQuery, SysLoginLogDto, ISysLoginLogService> {

    /** 定义节点名称 */
    @Override
    protected String getNodeName() {
        return "访问日志";
    }

}
