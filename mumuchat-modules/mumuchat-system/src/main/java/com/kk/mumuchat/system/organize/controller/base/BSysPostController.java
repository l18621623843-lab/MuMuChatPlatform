package com.kk.mumuchat.system.organize.controller.base;

import com.kk.mumuchat.common.core.constant.basic.BaseConstants;
import com.kk.mumuchat.common.core.utils.core.StrUtil;
import com.kk.mumuchat.common.web.entity.controller.BaseController;
import com.kk.mumuchat.system.api.organize.domain.dto.SysPostDto;
import com.kk.mumuchat.system.api.organize.domain.query.SysPostQuery;
import com.kk.mumuchat.system.organize.service.ISysDeptService;
import com.kk.mumuchat.system.organize.service.ISysPostService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 系统服务|组织模块|岗位管理|通用 业务处理
 *
 * @author mumuchat
 */
public class BSysPostController extends BaseController<SysPostQuery, SysPostDto, ISysPostService> {

    @Autowired
    protected ISysDeptService deptService;

    /** 定义节点名称 */
    @Override
    protected String getNodeName() {
        return "岗位";
    }

    /** 定义父数据名称 */
    protected String getParentName() {
        return "部门";
    }

    /**
     * 前置校验 增加/修改
     */
    @Override
    protected void AEHandle(BaseConstants.Operate operate, SysPostDto post) {
        switch (operate) {
            case EDIT_STATUS -> {
                if (StrUtil.equals(BaseConstants.Status.NORMAL.getCode(), post.getStatus())) {
                    SysPostDto original = baseService.selectById(post.getId());
                    if (BaseConstants.Status.DISABLE == deptService.checkStatus(original.getDeptId()))
                        warn(StrUtil.format("启用失败，该{}归属的{}已被禁用！", getNodeName(), getParentName()));
                }
            }
            case ADD, EDIT -> {
                if (baseService.checkNameUnique(post.getId(), post.getName()))
                    warn(StrUtil.format("{}{}{}失败，岗位名称已存在", operate.getInfo(), getNodeName(), post.getName()));
                if (BaseConstants.Status.DISABLE == deptService.checkStatus(post.getDeptId()))
                    post.setStatus(BaseConstants.Status.DISABLE.getCode());
            }
        }
    }

}
