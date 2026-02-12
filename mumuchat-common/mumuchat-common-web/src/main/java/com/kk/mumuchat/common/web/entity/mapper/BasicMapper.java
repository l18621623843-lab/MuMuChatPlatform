package com.kk.mumuchat.common.web.entity.mapper;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.github.yulichang.base.MPJBaseMapper;
import com.kk.mumuchat.common.core.utils.core.ArrayUtil;
import com.kk.mumuchat.common.core.web.entity.base.BasisEntity;
import com.kk.mumuchat.common.web.annotation.AutoInject;
import com.kk.mumuchat.common.web.conditions.LambdaQueryWrapper;
import com.kk.mumuchat.common.web.correlate.domain.SqlField;
import com.kk.mumuchat.common.web.correlate.utils.SqlHandleUtil;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 数据层 基类通用数据处理
 *
 * @param <P> Po
 * @author xueyi
 */
public interface BasicMapper<P extends BasisEntity> extends MPJBaseMapper<P> {

    /**
     * 自定义批量插入
     */
    @AutoInject
    int insertBatch(@Param("collection") Collection<P> list);

    /**
     * 自定义批量更新，条件为主键
     */
    @AutoInject(key = false, isInsert = false)
    int updateBatch(@Param("collection") Collection<P> list);

    default List<P> selectList() {
        return selectList(new LambdaQueryWrapper<>());
    }

    default List<P> selectList(SFunction<P, ?> field, Object value) {
        return selectList(new LambdaQueryWrapper<P>().eq(field, value));
    }

    default List<P> selectList(SFunction<P, ?> field, Collection<?> values) {
        return selectList(new LambdaQueryWrapper<P>().in(field, values));
    }

    default P selectOne(SFunction<P, ?> field, Object value) {
        return selectOne(new LambdaQueryWrapper<P>().eq(field, value));
    }

    /**
     * 根据动态SQL控制对象查询数据对象集合
     *
     * @param field 动态SQL控制对象
     * @return 数据对象集合
     */
    default List<P> selectListByField(SqlField... field) {
        if (ArrayUtil.isNotEmpty(field)) {
            return selectList(
                    Wrappers.<P>query().lambda()
                            .func(i -> SqlHandleUtil.fieldCondition(i, field)));
        }
        return new ArrayList<>();
    }

}
