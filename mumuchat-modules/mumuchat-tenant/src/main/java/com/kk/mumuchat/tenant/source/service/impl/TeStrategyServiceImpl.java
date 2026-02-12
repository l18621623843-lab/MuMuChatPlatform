package com.kk.mumuchat.tenant.source.service.impl;

import com.kk.mumuchat.common.cache.model.CacheModel;
import com.kk.mumuchat.common.core.constant.basic.DictConstants;
import com.kk.mumuchat.common.core.utils.core.ObjectUtil;
import com.kk.mumuchat.common.core.utils.core.StrUtil;
import com.kk.mumuchat.common.web.entity.service.impl.BaseServiceImpl;
import com.kk.mumuchat.tenant.api.source.constant.SourceConstants;
import com.kk.mumuchat.tenant.api.source.domain.dto.TeStrategyDto;
import com.kk.mumuchat.tenant.api.source.domain.query.TeStrategyQuery;
import com.kk.mumuchat.tenant.source.domain.correlate.TeStrategyCorrelate;
import com.kk.mumuchat.tenant.source.manager.ITeStrategyManager;
import com.kk.mumuchat.tenant.source.service.ITeStrategyService;
import org.springframework.stereotype.Service;

/**
 * 租户服务 | 策略模块 | 源策略管理 服务层处理
 *
 * @author xueyi
 */
@Service
public class TeStrategyServiceImpl extends BaseServiceImpl<TeStrategyQuery, TeStrategyDto, TeStrategyCorrelate, ITeStrategyManager> implements ITeStrategyService {

    /** 缓存定义 */
    @Override
    public CacheModel getCacheModel() {
        return new CacheModel(SourceConstants.CacheType.TE_STRATEGY_KEY);
    }

    /**
     * 校验数据源是否被使用
     *
     * @param sourceId 数据源id
     * @return 结果 | true/false 存在/不存在
     */
    @Override
    public boolean checkSourceExist(Long sourceId) {
        return ObjectUtil.isNotNull(baseManager.checkSourceExist(sourceId));
    }

    /**
     * 校验源策略是否为默认源策略
     *
     * @param id 源策略id
     * @return 结果 | true/false 是/不是
     */
    @Override
    public boolean checkIsDefault(Long id) {
        TeStrategyDto strategy = baseManager.selectById(id);
        return ObjectUtil.isNotNull(strategy) && StrUtil.equals(strategy.getIsDefault(), DictConstants.DicYesNo.YES.getCode());
    }

}