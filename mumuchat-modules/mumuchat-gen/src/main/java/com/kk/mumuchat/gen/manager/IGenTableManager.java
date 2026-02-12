package com.kk.mumuchat.gen.manager;

import com.kk.mumuchat.common.web.entity.manager.IBaseManager;
import com.kk.mumuchat.gen.domain.dto.GenTableDto;
import com.kk.mumuchat.gen.domain.query.GenTableQuery;

import java.util.List;

/**
 * 业务管理 数据封装层
 *
 * @author xueyi
 */
public interface IGenTableManager extends IBaseManager<GenTableQuery, GenTableDto> {

    /**
     * 查询数据库列表 | 主库
     *
     * @param table 业务对象
     * @return 数据库表集合
     */
    List<GenTableDto> selectDbTableList(GenTableQuery table);

    /**
     * 根据表名称组查询数据库列表 | 主库
     *
     * @param names 表名称组
     * @return 数据库表集合
     */
    List<GenTableDto> selectDbTableListByNames(String[] names);
}
