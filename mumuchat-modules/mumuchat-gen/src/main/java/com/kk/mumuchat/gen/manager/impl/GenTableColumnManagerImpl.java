package com.kk.mumuchat.gen.manager.impl;

import com.kk.mumuchat.common.datasource.annotation.Isolate;
import com.kk.mumuchat.common.web.entity.manager.impl.BaseManagerImpl;
import com.kk.mumuchat.gen.domain.dto.GenTableColumnDto;
import com.kk.mumuchat.gen.domain.model.GenTableColumnConverter;
import com.kk.mumuchat.gen.domain.po.GenTableColumnPo;
import com.kk.mumuchat.gen.domain.query.GenTableColumnQuery;
import com.kk.mumuchat.gen.manager.IGenTableColumnManager;
import com.kk.mumuchat.gen.mapper.GenTableColumnMapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 业务字段管理 数据封装层处理
 *
 * @author mumuchat
 */
@Component
public class GenTableColumnManagerImpl extends BaseManagerImpl<GenTableColumnQuery, GenTableColumnDto, GenTableColumnPo, GenTableColumnMapper, GenTableColumnConverter> implements IGenTableColumnManager {

    /**
     * 根据表名称查询数据库表列信息
     *
     * @param tableName 表名称
     * @return 数据库表列信息
     */
    @Isolate
    @Override
    public List<GenTableColumnDto> selectDbTableColumnsByName(String tableName) {
        return baseMapper.selectDbTableColumnsByName(tableName);
    }
}
