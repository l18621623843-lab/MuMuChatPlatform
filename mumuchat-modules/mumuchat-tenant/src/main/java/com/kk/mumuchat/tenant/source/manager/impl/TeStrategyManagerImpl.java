package com.kk.mumuchat.tenant.source.manager.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.kk.mumuchat.common.core.constant.basic.SqlConstants;
import com.kk.mumuchat.common.web.entity.manager.impl.BaseManagerImpl;
import com.kk.mumuchat.tenant.api.source.domain.dto.TeStrategyDto;
import com.kk.mumuchat.tenant.api.source.domain.po.TeStrategyPo;
import com.kk.mumuchat.tenant.api.source.domain.query.TeStrategyQuery;
import com.kk.mumuchat.tenant.source.domain.model.TeStrategyConverter;
import com.kk.mumuchat.tenant.source.manager.ITeStrategyManager;
import com.kk.mumuchat.tenant.source.mapper.TeStrategyMapper;
import org.springframework.stereotype.Component;


/**
 * 租户服务 | 策略模块 | 源策略管理 数据封装层处理
 *
 * @author mumuchat
 */
@Component
public class TeStrategyManagerImpl extends BaseManagerImpl<TeStrategyQuery, TeStrategyDto, TeStrategyPo, TeStrategyMapper, TeStrategyConverter> implements ITeStrategyManager {

    /**
     * 校验数据源是否被使用
     *
     * @param sourceId 数据源id
     * @return 结果
     */
    @Override
    public TeStrategyDto checkSourceExist(Long sourceId) {
        TeStrategyPo strategy = baseMapper.selectOne(
                Wrappers.<TeStrategyPo>lambdaQuery()
                        .eq(TeStrategyPo::getSourceId, sourceId)
                        .last(SqlConstants.LIMIT_ONE));
        return baseConverter.mapperDto(strategy);
    }
}