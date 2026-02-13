package com.kk.mumuchat.system.monitor.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.kk.mumuchat.common.core.utils.core.ObjectUtil;
import com.kk.mumuchat.common.web.entity.manager.impl.BaseManagerImpl;
import com.kk.mumuchat.system.api.log.domain.dto.SysOperateLogDto;
import com.kk.mumuchat.system.api.log.domain.po.SysOperateLogPo;
import com.kk.mumuchat.system.api.log.domain.query.SysOperateLogQuery;
import com.kk.mumuchat.system.monitor.domain.model.SysOperateLogConverter;
import com.kk.mumuchat.system.monitor.manager.ISysOperateLogManager;
import com.kk.mumuchat.system.monitor.mapper.SysOperateLogMapper;
import org.springframework.stereotype.Component;

/**
 * 系统服务|监控模块|操作日志管理 数据封装层处理
 *
 * @author mumuchat
 */
@Component
public class SysOperateLogManagerImpl extends BaseManagerImpl<SysOperateLogQuery, SysOperateLogDto, SysOperateLogPo, SysOperateLogMapper, SysOperateLogConverter> implements ISysOperateLogManager {

    /**
     * 清空系统操作日志
     */
    @Override
    public void cleanOperateLog() {
        baseMapper.delete(Wrappers.query());
    }

    /**
     * 查询条件构造|列表查询
     *
     * @param query 数据查询对象
     */
    @Override
    protected LambdaQueryWrapper<SysOperateLogPo> selectListQuery(SysOperateLogQuery query) {
        return super.selectListQuery(query)
                .func(i -> {
                    if (ObjectUtil.isAllNotEmpty(query.getOperateTimeStart(), query.getOperateTimeEnd())) {
                        i.between(SysOperateLogPo::getOperateTime, query.getOperateTimeStart(), query.getOperateTimeEnd());
                    }
                });
    }
}
