package com.kk.mumuchat.system.dict.controller.base;

import com.kk.mumuchat.common.web.entity.controller.BaseController;
import com.kk.mumuchat.system.api.dict.domain.dto.SysDictDataDto;
import com.kk.mumuchat.system.api.dict.domain.query.SysDictDataQuery;
import com.kk.mumuchat.system.dict.service.ISysDictDataService;

/**
 * 系统服务|字典模块|字典数据管理|通用 业务处理
 *
 * @author mumuchat
 */
public class BSysDictDataController extends BaseController<SysDictDataQuery, SysDictDataDto, ISysDictDataService> {

    /** 定义节点名称 */
    @Override
    protected String getNodeName() {
        return "字典数据";
    }
}
