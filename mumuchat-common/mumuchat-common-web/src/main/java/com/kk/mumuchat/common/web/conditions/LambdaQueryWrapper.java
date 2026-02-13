package com.kk.mumuchat.common.web.conditions;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.kk.mumuchat.common.core.utils.core.ArrayUtil;
import com.kk.mumuchat.common.core.utils.core.ObjectUtil;
import com.kk.mumuchat.common.core.utils.core.StrUtil;
import lombok.NoArgsConstructor;

import java.util.Collection;

/**
 * MyBatis Plus QueryWrapper 拓展类
 *
 * @author mumuchat
 */
@NoArgsConstructor
public class LambdaQueryWrapper<T> extends com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<T> {

    public LambdaQueryWrapper(T entity) {
        super(entity);
    }

    public LambdaQueryWrapper(Class<T> entityClass) {
        super(entityClass);
    }

    public LambdaQueryWrapper<T> likeIfPresent(SFunction<T, ?> column, String val) {
        if (StrUtil.hasText(val)) {
            return (LambdaQueryWrapper<T>) super.like(column, val);
        }
        return this;
    }

    public LambdaQueryWrapper<T> inIfPresent(SFunction<T, ?> column, Collection<?> values) {
        if (ObjectUtil.isAllNotEmpty(values) && !ArrayUtil.isEmpty(values)) {
            return (LambdaQueryWrapper<T>) super.in(column, values);
        }
        return this;
    }

    public LambdaQueryWrapper<T> inIfPresent(SFunction<T, ?> column, Object... values) {
        if (ObjectUtil.isAllNotEmpty(values) && !ArrayUtil.isEmpty(values)) {
            return (LambdaQueryWrapper<T>) super.in(column, values);
        }
        return this;
    }

    public LambdaQueryWrapper<T> eqIfPresent(SFunction<T, ?> column, Object val) {
        if (ObjectUtil.isNotEmpty(val)) {
            return (LambdaQueryWrapper<T>) super.eq(column, val);
        }
        return this;
    }

    public LambdaQueryWrapper<T> neIfPresent(SFunction<T, ?> column, Object val) {
        if (ObjectUtil.isNotEmpty(val)) {
            return (LambdaQueryWrapper<T>) super.ne(column, val);
        }
        return this;
    }

    public LambdaQueryWrapper<T> gtIfPresent(SFunction<T, ?> column, Object val) {
        if (val != null) {
            return (LambdaQueryWrapper<T>) super.gt(column, val);
        }
        return this;
    }

    public LambdaQueryWrapper<T> geIfPresent(SFunction<T, ?> column, Object val) {
        if (val != null) {
            return (LambdaQueryWrapper<T>) super.ge(column, val);
        }
        return this;
    }

    public LambdaQueryWrapper<T> ltIfPresent(SFunction<T, ?> column, Object val) {
        if (val != null) {
            return (LambdaQueryWrapper<T>) super.lt(column, val);
        }
        return this;
    }

    public LambdaQueryWrapper<T> leIfPresent(SFunction<T, ?> column, Object val) {
        if (val != null) {
            return (LambdaQueryWrapper<T>) super.le(column, val);
        }
        return this;
    }

    public LambdaQueryWrapper<T> betweenIfPresent(SFunction<T, ?> column, Object val1, Object val2) {
        if (val1 != null && val2 != null) {
            return (LambdaQueryWrapper<T>) super.between(column, val1, val2);
        }
        if (val1 != null) {
            return (LambdaQueryWrapper<T>) ge(column, val1);
        }
        if (val2 != null) {
            return (LambdaQueryWrapper<T>) le(column, val2);
        }
        return this;
    }

    public LambdaQueryWrapper<T> betweenIfPresent(SFunction<T, ?> column, Object[] values) {
        Object val1 = ArrayUtil.get(values, 0);
        Object val2 = ArrayUtil.get(values, 1);
        return betweenIfPresent(column, val1, val2);
    }

    // ========== 重写父类方法，方便链式调用 ==========

    @Override
    public LambdaQueryWrapper<T> eq(boolean condition, SFunction<T, ?> column, Object val) {
        super.eq(condition, column, val);
        return this;
    }

    @Override
    public LambdaQueryWrapper<T> eq(SFunction<T, ?> column, Object val) {
        super.eq(column, val);
        return this;
    }

    @Override
    public LambdaQueryWrapper<T> orderByDesc(SFunction<T, ?> column) {
        super.orderByDesc(true, column);
        return this;
    }

    @Override
    public LambdaQueryWrapper<T> last(String lastSql) {
        super.last(lastSql);
        return this;
    }

    @Override
    public LambdaQueryWrapper<T> in(SFunction<T, ?> column, Collection<?> coll) {
        super.in(column, coll);
        return this;
    }

}
