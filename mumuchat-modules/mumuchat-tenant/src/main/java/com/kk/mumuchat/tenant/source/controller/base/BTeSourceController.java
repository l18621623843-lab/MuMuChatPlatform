package com.kk.mumuchat.tenant.source.controller.base;

import com.kk.mumuchat.common.core.constant.basic.BaseConstants;
import com.kk.mumuchat.common.core.utils.core.CollUtil;
import com.kk.mumuchat.common.core.utils.core.SpringUtil;
import com.kk.mumuchat.common.core.utils.core.StrUtil;
import com.kk.mumuchat.common.datasource.utils.DSUtil;
import com.kk.mumuchat.common.web.entity.controller.BaseController;
import com.kk.mumuchat.tenant.api.source.domain.dto.TeSourceDto;
import com.kk.mumuchat.tenant.api.source.domain.query.TeSourceQuery;
import com.kk.mumuchat.tenant.source.config.SourceProperties;
import com.kk.mumuchat.tenant.source.service.ITeSourceService;
import com.kk.mumuchat.tenant.source.service.ITeStrategyService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

/**
 * 租户服务 | 策略模块 | 数据源管理 | 通用 业务处理
 *
 * @author xueyi
 */
public class BTeSourceController extends BaseController<TeSourceQuery, TeSourceDto, ITeSourceService> {

    @Autowired
    private ITeStrategyService strategyService;

    /** 定义节点名称 */
    @Override
    protected String getNodeName() {
        return "数据源";
    }

    /**
     * 前置校验 增加/修改
     */
    @Override
    protected void AEHandle(BaseConstants.Operate operate, TeSourceDto source) {
        testSlaveDs(source);
        if (baseService.checkNameUnique(source.getId(), source.getName())) {
            warn(StrUtil.format("{}{}{}失败，{}名称已存在", operate.getInfo(), getNodeName(), source.getName(), getNodeName()));
        }
    }

    /**
     * 前置校验 删除
     */
    @Override
    protected void RHandle(BaseConstants.Operate operate, List<Long> idList) {
        int size = idList.size();
        for (int i = idList.size() - 1; i >= 0; i--) {
            if (strategyService.checkSourceExist(idList.get(i))) {
                idList.remove(i);
            } else if (baseService.checkIsDefault(idList.get(i))) {
                idList.remove(i);
            }
        }
        if (CollUtil.isEmpty(idList)) {
            warn(StrUtil.format("删除失败，默认{}及已被使用的{}不允许删除！", getNodeName(), getNodeName()));
        } else if (idList.size() != size) {
            baseService.deleteByIds(idList);
            warn(StrUtil.format("默认{}及已被使用的{}不允许删除，其余{}删除成功！", getNodeName(), getNodeName(), getNodeName()));
        }
    }

    /**
     * 测试数据源是否为可连接子库
     *
     * @param source 数据源对象
     */
    public static void testSlaveDs(TeSourceDto source) {
        String[] slaveTables = SpringUtil.getBean(SourceProperties.class).getSlaveTable();
        DSUtil.testSlaveDs(source, Arrays.stream(slaveTables).toList());
    }
}