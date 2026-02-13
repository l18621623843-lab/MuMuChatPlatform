package com.kk.mumuchat.gen.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.kk.mumuchat.common.web.entity.mapper.BaseMapper;
import com.kk.mumuchat.gen.domain.dto.GenTableColumnDto;
import com.kk.mumuchat.gen.domain.po.GenTableColumnPo;
import com.kk.mumuchat.gen.domain.query.GenTableColumnQuery;

import java.util.List;

/**
 * 业务字段管理 数据层
 *
 * @author mumuchat
 */
public interface GenTableColumnMapper extends BaseMapper<GenTableColumnQuery, GenTableColumnDto, GenTableColumnPo> {

    /**
     * 根据表名称查询数据库表列信息
     *
     * @param tableName 表名称
     * @return 数据库表列信息
     */
    @InterceptorIgnore(tenantLine = "1")
    List<GenTableColumnDto> selectDbTableColumnsByName(String tableName);

}