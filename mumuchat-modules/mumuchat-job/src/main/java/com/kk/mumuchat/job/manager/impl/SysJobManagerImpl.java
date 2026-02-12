package com.kk.mumuchat.job.manager.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.kk.mumuchat.common.web.annotation.TenantIgnore;
import com.kk.mumuchat.common.web.entity.manager.impl.BaseManagerImpl;
import com.kk.mumuchat.job.api.domain.dto.SysJobDto;
import com.kk.mumuchat.job.api.domain.po.SysJobPo;
import com.kk.mumuchat.job.api.domain.query.SysJobQuery;
import com.kk.mumuchat.job.domain.model.SysJobConverter;
import com.kk.mumuchat.job.manager.ISysJobManager;
import com.kk.mumuchat.job.mapper.SysJobMapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 调度任务管理 数据封装层处理
 *
 * @author xueyi
 */
@Component
public class SysJobManagerImpl extends BaseManagerImpl<SysJobQuery, SysJobDto, SysJobPo, SysJobMapper, SysJobConverter> implements ISysJobManager {

    /**
     * 项目启动时
     */
    @Override
    @TenantIgnore
    public List<SysJobDto> initScheduler() {
        List<SysJobPo> jobList = baseMapper.selectList(Wrappers.query());
        return baseConverter.mapperDto(jobList);
    }
}
