package com.kk.mumuchat.common.web.conditions;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.github.yulichang.toolkit.MPJWrappers;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.kk.mumuchat.common.core.utils.core.ArrayUtil;
import com.kk.mumuchat.common.core.utils.core.ObjectUtil;
import com.kk.mumuchat.common.core.utils.core.StrUtil;
import lombok.NoArgsConstructor;

import java.util.Collection;

/**
 * MyBatis Plus Join QueryWrapper 拓展类
 *
 * @author xueyi
 */
@NoArgsConstructor
public class JoinLambdaWrapper<T> extends MPJLambdaWrapper<T> {

    public JoinLambdaWrapper(Class<T> clazz) {
        super(clazz);
    }

    public JoinLambdaWrapper(T entity) {
        super(entity);
    }

    public JoinLambdaWrapper(String alias) {
        super(alias);
    }

    public JoinLambdaWrapper(Class<T> clazz, String alias) {
        super(clazz, alias);
    }

    public JoinLambdaWrapper(T entity, String alias) {
        super(entity, alias);
    }

    public JoinLambdaWrapper<T> likeIfPresent(SFunction<T, ?> column, String val) {
        MPJWrappers.lambdaJoin().like(column, val);
        if (StrUtil.hasText(val)) {
            return (JoinLambdaWrapper<T>) super.like(column, val);
        }
        return this;
    }

    public JoinLambdaWrapper<T> inIfPresent(SFunction<T, ?> column, Collection<?> values) {
        if (ObjectUtil.isAllNotEmpty(values) && !ArrayUtil.isEmpty(values)) {
            return (JoinLambdaWrapper<T>) super.in(column, values);
        }
        return this;
    }

    public JoinLambdaWrapper<T> inIfPresent(SFunction<T, ?> column, Object... values) {
        if (ObjectUtil.isAllNotEmpty(values) && !ArrayUtil.isEmpty(values)) {
            return (JoinLambdaWrapper<T>) super.in(column, values);
        }
        return this;
    }

    public JoinLambdaWrapper<T> eqIfPresent(SFunction<T, ?> column, Object val) {
        if (ObjectUtil.isNotEmpty(val)) {
            return (JoinLambdaWrapper<T>) super.eq(column, val);
        }
        return this;
    }

    public JoinLambdaWrapper<T> neIfPresent(SFunction<T, ?> column, Object val) {
        if (ObjectUtil.isNotEmpty(val)) {
            return (JoinLambdaWrapper<T>) super.ne(column, val);
        }
        return this;
    }

    public JoinLambdaWrapper<T> gtIfPresent(SFunction<T, ?> column, Object val) {
        if (val != null) {
            return (JoinLambdaWrapper<T>) super.gt(column, val);
        }
        return this;
    }

    public JoinLambdaWrapper<T> geIfPresent(SFunction<T, ?> column, Object val) {
        if (val != null) {
            return (JoinLambdaWrapper<T>) super.ge(column, val);
        }
        return this;
    }

    public JoinLambdaWrapper<T> ltIfPresent(SFunction<T, ?> column, Object val) {
        if (val != null) {
            return (JoinLambdaWrapper<T>) super.lt(column, val);
        }
        return this;
    }

    public JoinLambdaWrapper<T> leIfPresent(SFunction<T, ?> column, Object val) {
        if (val != null) {
            return (JoinLambdaWrapper<T>) super.le(column, val);
        }
        return this;
    }

    public JoinLambdaWrapper<T> betweenIfPresent(SFunction<T, ?> column, Object val1, Object val2) {
        if (val1 != null && val2 != null) {
            return (JoinLambdaWrapper<T>) super.between(column, val1, val2);
        }
        if (val1 != null) {
            return (JoinLambdaWrapper<T>) ge(column, val1);
        }
        if (val2 != null) {
            return (JoinLambdaWrapper<T>) le(column, val2);
        }
        return this;
    }

    public JoinLambdaWrapper<T> betweenIfPresent(SFunction<T, ?> column, Object[] values) {
        Object val1 = ArrayUtil.get(values, 0);
        Object val2 = ArrayUtil.get(values, 1);
        return betweenIfPresent(column, val1, val2);
    }
}
