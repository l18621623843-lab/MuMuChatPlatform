package com.kk.mumuchat.gen.service;

import com.kk.mumuchat.common.web.entity.service.IBaseService;
import com.kk.mumuchat.gen.domain.dto.GenTableColumnDto;
import com.kk.mumuchat.gen.domain.query.GenTableColumnQuery;

import java.util.List;

/**
 * 业务字段管理 服务层
 *
 * @author mumuchat
 */
public interface IGenTableColumnService extends IBaseService<GenTableColumnQuery, GenTableColumnDto> {

    /**
     * 根据表名称查询数据库表列信息
     *
     * @param tableName  表名称
     * @param sourceName 数据源
     * @return 数据库表列信息
     */
    List<GenTableColumnDto> selectDbTableColumnsByName(String tableName, String sourceName);
}