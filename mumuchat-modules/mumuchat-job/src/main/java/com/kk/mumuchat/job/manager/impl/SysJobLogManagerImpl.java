package com.kk.mumuchat.job.manager.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.kk.mumuchat.common.web.entity.manager.impl.BaseManagerImpl;
import com.kk.mumuchat.job.api.domain.dto.SysJobLogDto;
import com.kk.mumuchat.job.api.domain.po.SysJobLogPo;
import com.kk.mumuchat.job.api.domain.query.SysJobLogQuery;
import com.kk.mumuchat.job.domain.model.SysJobLogConverter;
import com.kk.mumuchat.job.manager.ISysJobLogManager;
import com.kk.mumuchat.job.mapper.SysJobLogMapper;
import org.springframework.stereotype.Component;

/**
 * 调度任务日志管理 数据封装层处理
 *
 * @author mumuchat
 */
@Component
public class SysJobLogManagerImpl extends BaseManagerImpl<SysJobLogQuery, SysJobLogDto, SysJobLogPo, SysJobLogMapper, SysJobLogConverter> implements ISysJobLogManager {

    /**
     * 清空任务日志
     */
    @Override
    public void cleanLog() {
        baseMapper.delete(Wrappers.update());
    }
}
