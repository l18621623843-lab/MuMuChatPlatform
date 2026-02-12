package com.kk.mumuchat.common.datascope.interceptor;

import com.baomidou.mybatisplus.extension.plugins.handler.DataPermissionHandler;
import com.kk.mumuchat.common.core.constant.basic.BaseConstants;
import com.kk.mumuchat.common.core.constant.basic.SecurityConstants;
import com.kk.mumuchat.common.core.utils.core.ArrayUtil;
import com.kk.mumuchat.common.core.utils.core.CollUtil;
import com.kk.mumuchat.common.core.utils.core.NumberUtil;
import com.kk.mumuchat.common.core.utils.core.ObjectUtil;
import com.kk.mumuchat.common.core.utils.core.StrUtil;
import com.kk.mumuchat.common.datascope.annotation.DataScope;
import com.kk.mumuchat.common.security.utils.SecurityUserUtils;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.HexValue;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.expression.operators.relational.ParenthesedExpressionList;
import net.sf.jsqlparser.schema.Column;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 数据权限处理器
 *
 * @author xueyi
 */
@Aspect
@Component
public class XueYiDataScopeHandler implements DataPermissionHandler {

    /**
     * 通过ThreadLocal记录权限相关的属性值
     */
    public ThreadLocal<DataScope> threadLocal = new ThreadLocal<>();

    /**
     * 清空当前线程上次保存的权限信息
     */
    @After("@annotation(controllerDataScope)")
    public void clearThreadLocal(DataScope controllerDataScope) {
        threadLocal.remove();
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    @Before("@annotation(controllerDataScope)")
    public void doBefore(DataScope controllerDataScope) {
        // 获得注解
        if (controllerDataScope != null) threadLocal.set(controllerDataScope);
    }

    /**
     * @param where             原SQL Where 条件表达式
     * @param mappedStatementId Mapper接口方法ID
     * @return 结果
     */
    @Override
    public Expression getSqlSegment(Expression where, String mappedStatementId) {
        DataScope dataScope = threadLocal.get();
        if (ObjectUtil.isNull(dataScope) || (StrUtil.isAllEmpty(dataScope.deptAlias(), dataScope.postAlias(), dataScope.userAlias())))
            return where;
        List<String> split = StrUtil.split(mappedStatementId, StrUtil.DOT);
        int index = split.size();
        String method = split.get(index - NumberUtil.One);
        String mapper = split.get(index - NumberUtil.Two);
        if (!((ArrayUtil.isEmpty(dataScope.mapperScope()) || ArrayUtil.contains(dataScope.mapperScope(), mapper)) && (ArrayUtil.isEmpty(dataScope.methodScope()) || ArrayUtil.contains(dataScope.methodScope(), method))))
            return where;
        if (where == null) where = new HexValue(" 1 = 1 ");
        com.kk.mumuchat.system.api.model.DataScope scope = SecurityUserUtils.getDataScope();
        if (ObjectUtil.isNull(scope) || scope.isAdmin()) return where;
        Long userId = scope.getUserId();
        String scopeType = scope.getDataScope();
        switch (SecurityConstants.DataScope.getByCode(scopeType)) {
            case ALL -> {
                return where;
            }
            case CUSTOM, DEPT, DEPT_AND_CHILD, POST -> {
                if (StrUtil.isNotEmpty(dataScope.userAlias())) {
                    Set<Long> userScope = scope.getUserScope();
                    InExpression userInExpression = new InExpression(new Column(dataScope.userAlias()), new ParenthesedExpressionList<>(
                            Optional.ofNullable(userScope)
                                    .filter(CollUtil::isNotEmpty)
                                    .map(list -> list.stream().map(LongValue::new).collect(Collectors.toList()))
                                    .orElseGet(() -> new ArrayList<>() {{
                                        add(new LongValue(BaseConstants.NONE_ID));
                                    }}))
                    );
                    return new AndExpression(where, userInExpression);
                } else if (StrUtil.isNotEmpty(dataScope.postAlias())) {
                    Set<Long> postScope = scope.getPostScope();
                    InExpression postInExpression = new InExpression(new Column(dataScope.postAlias()), new ParenthesedExpressionList<>(
                            Optional.ofNullable(postScope)
                                    .filter(CollUtil::isNotEmpty)
                                    .map(list -> list.stream().map(LongValue::new).collect(Collectors.toList()))
                                    .orElseGet(() -> CollUtil.singleList(new LongValue(BaseConstants.NONE_ID)))));
                    return new AndExpression(where, postInExpression);
                } else if (StrUtil.isNotEmpty(dataScope.deptAlias())) {
                    Set<Long> deptScope = scope.getDeptScope();
                    InExpression deptInExpression = new InExpression(new Column(dataScope.deptAlias()), new ParenthesedExpressionList<>(
                            Optional.ofNullable(deptScope)
                                    .filter(CollUtil::isNotEmpty)
                                    .map(list -> list.stream().map(LongValue::new).collect(Collectors.toList()))
                                    .orElseGet(() -> CollUtil.singleList(new LongValue(BaseConstants.NONE_ID)))));
                    return new AndExpression(where, deptInExpression);
                }
            }
            case SELF -> {
                if (StrUtil.isNotEmpty(dataScope.userAlias())) {
                    EqualsTo selfEqualsTo = new EqualsTo();
                    selfEqualsTo.setLeftExpression(new Column(dataScope.userAlias()));
                    selfEqualsTo.setRightExpression(new LongValue(userId));
                    return new AndExpression(where, selfEqualsTo);
                }
            }
            default -> {
            }
        }
        EqualsTo noEqualsTo = new EqualsTo();
        noEqualsTo.setLeftExpression(new Column("1"));
        noEqualsTo.setRightExpression(new LongValue("0"));
        return new AndExpression(where, noEqualsTo);
    }
}
