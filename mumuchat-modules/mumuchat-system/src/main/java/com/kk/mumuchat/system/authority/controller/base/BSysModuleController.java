package com.kk.mumuchat.system.authority.controller.base;

import com.kk.mumuchat.common.core.constant.basic.BaseConstants;
import com.kk.mumuchat.common.core.context.SecurityContextHolder;
import com.kk.mumuchat.common.core.utils.core.StrUtil;
import com.kk.mumuchat.common.security.utils.SecurityUserUtils;
import com.kk.mumuchat.common.web.entity.controller.BaseController;
import com.kk.mumuchat.system.api.authority.domain.dto.SysModuleDto;
import com.kk.mumuchat.system.api.authority.domain.query.SysModuleQuery;
import com.kk.mumuchat.system.authority.service.ISysModuleService;

/**
 * 系统服务|权限模块|模块管理|通用 业务处理
 *
 * @author mumuchat
 */
public class BSysModuleController extends BaseController<SysModuleQuery, SysModuleDto, ISysModuleService> {

    /** 定义节点名称 */
    @Override
    protected String getNodeName() {
        return "模块";
    }

    /**
     * 前置校验 新增/修改
     */
    @Override
    protected void AEHandle(BaseConstants.Operate operate, SysModuleDto module) {
        Boolean isNotUnique = SecurityContextHolder.setTenantIgnoreFun(() -> baseService.checkNameUnique(module.getId(), module.getName()));
        if (isNotUnique) {
            warn(StrUtil.format("{}{}{}失败，{}名称已存在！", operate.getInfo(), getNodeName(), module.getName(), getNodeName()));
        }
        if (module.isCommon() && SecurityUserUtils.isNotAdminTenant()) {
            warn(StrUtil.format("{}{}{}失败，无操作权限！", operate.getInfo(), getNodeName(), module.getName()));
        }
    }
}
