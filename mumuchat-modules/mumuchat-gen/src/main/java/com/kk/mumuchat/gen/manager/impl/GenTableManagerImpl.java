package com.kk.mumuchat.gen.manager.impl;

import com.kk.mumuchat.common.datasource.annotation.Isolate;
import com.kk.mumuchat.common.web.entity.manager.impl.BaseManagerImpl;
import com.kk.mumuchat.gen.domain.dto.GenTableDto;
import com.kk.mumuchat.gen.domain.model.GenTableConverter;
import com.kk.mumuchat.gen.domain.po.GenTablePo;
import com.kk.mumuchat.gen.domain.query.GenTableQuery;
import com.kk.mumuchat.gen.manager.IGenTableManager;
import com.kk.mumuchat.gen.mapper.GenTableMapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 业务管理 数据封装层处理
 *
 * @author mumuchat
 */
@Component
public class GenTableManagerImpl extends BaseManagerImpl<GenTableQuery, GenTableDto, GenTablePo, GenTableMapper, GenTableConverter> implements IGenTableManager {

    /**
     * 查询数据库列表
     *
     * @param table 业务对象
     * @return 数据库表集合
     */
    @Isolate
    @Override
    public List<GenTableDto> selectDbTableList(GenTableQuery table) {
        return baseMapper.selectDbTableList(table);
    }

    /**
     * 根据表名称组查询数据库列表 | 主库
     *
     * @param names 表名称组
     * @return 数据库表集合
     */
    @Isolate
    @Override
    public List<GenTableDto> selectDbTableListByNames(String[] names) {
        return baseMapper.selectDbTableListByNames(names);
    }
}
