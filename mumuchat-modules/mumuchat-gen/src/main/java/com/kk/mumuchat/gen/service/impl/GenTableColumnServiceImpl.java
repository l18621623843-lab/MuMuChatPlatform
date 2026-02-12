package com.kk.mumuchat.gen.service.impl;

import com.kk.mumuchat.common.core.constant.basic.TenantConstants;
import com.kk.mumuchat.common.core.context.SecurityContextHolder;
import com.kk.mumuchat.common.core.utils.core.StrUtil;
import com.kk.mumuchat.common.web.entity.service.impl.BaseServiceImpl;
import com.kk.mumuchat.gen.domain.correlate.GenTableColumnCorrelate;
import com.kk.mumuchat.gen.domain.dto.GenTableColumnDto;
import com.kk.mumuchat.gen.domain.query.GenTableColumnQuery;
import com.kk.mumuchat.gen.manager.IGenTableColumnManager;
import com.kk.mumuchat.gen.service.IGenTableColumnService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 业务字段管理 服务层实现
 *
 * @author xueyi
 */
@Service
public class GenTableColumnServiceImpl extends BaseServiceImpl<GenTableColumnQuery, GenTableColumnDto, GenTableColumnCorrelate, IGenTableColumnManager> implements IGenTableColumnService {

    /**
     * 根据表名称查询数据库表列信息
     *
     * @param tableName  表名称
     * @param sourceName 数据源
     * @return 数据库表列信息
     */
    @Override
    public List<GenTableColumnDto> selectDbTableColumnsByName(String tableName, String sourceName) {
        return SecurityContextHolder.setSourceNameFun(StrUtil.isNotBlank(sourceName) ? sourceName : TenantConstants.Source.MASTER.getCode(), () -> baseManager.selectDbTableColumnsByName(tableName));
    }
}