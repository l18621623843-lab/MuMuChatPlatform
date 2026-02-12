package com.kk.mumuchat.system.dict.controller.base;

import cn.hutool.core.collection.CollUtil;
import com.kk.mumuchat.common.cache.utils.DictUtil;
import com.kk.mumuchat.common.core.constant.basic.BaseConstants;
import com.kk.mumuchat.common.core.utils.core.ObjectUtil;
import com.kk.mumuchat.common.core.utils.core.StrUtil;
import com.kk.mumuchat.common.core.web.result.AjaxResult;
import com.kk.mumuchat.common.web.entity.controller.BaseController;
import com.kk.mumuchat.system.api.dict.domain.dto.SysDictTypeDto;
import com.kk.mumuchat.system.api.dict.domain.query.SysDictTypeQuery;
import com.kk.mumuchat.system.dict.service.ISysDictTypeService;

import java.util.List;
import java.util.Objects;

/**
 * 系统服务|字典模块|字典类型管理|通用 业务处理
 *
 * @author xueyi
 */
public class BSysDictTypeController extends BaseController<SysDictTypeQuery, SysDictTypeDto, ISysDictTypeService> {

    /** 定义节点名称 */
    @Override
    protected String getNodeName() {
        return "字典类型";
    }

    /**
     * 根据字典类型查询字典数据信息
     */
    public AjaxResult listByCode(String code) {
        if (StrUtil.isEmpty(code)) {
            warn("请传入编码后再查询字典");
        }
        return AjaxResult.success(DictUtil.getDictCache(code));
    }

    /**
     * 根据字典类型查询字典数据信息
     */
    public AjaxResult listByCodeList(List<String> codeList) {
        if (CollUtil.isEmpty(codeList)) {
            warn("请传入编码后再查询字典");
        }
        return AjaxResult.success(DictUtil.getDictCache(codeList));
    }

    /**
     * 前置校验 增加/修改
     */
    @Override
    protected void AEHandle(BaseConstants.Operate operate, SysDictTypeDto dictType) {
        if (Objects.requireNonNull(operate) == BaseConstants.Operate.ADD) {
            if (ObjectUtil.isNull(dictType.getTenantId()) && baseService.checkDictCodeUnique(dictType.getId(), dictType.getCode())) {
                warn(StrUtil.format("{}{}{}失败，字典编码已存在", operate.getInfo(), getNodeName(), dictType.getName()));
            }
        }
    }
}
