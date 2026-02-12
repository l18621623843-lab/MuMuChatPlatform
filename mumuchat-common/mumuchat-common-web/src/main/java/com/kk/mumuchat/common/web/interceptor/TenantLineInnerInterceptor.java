package com.kk.mumuchat.common.web.interceptor;

import com.baomidou.mybatisplus.core.toolkit.ExceptionUtils;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.kk.mumuchat.common.core.utils.core.CollUtil;
import com.kk.mumuchat.common.web.handler.TenantLineHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.ParenthesedExpressionList;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.Values;
import net.sf.jsqlparser.statement.update.UpdateSet;

import java.util.List;

/**
 * 租户拦截器
 *
 * @author xueyi
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuppressWarnings(value = {"unchecked", "rawtypes"})
public class TenantLineInnerInterceptor<T extends TenantLineHandler> extends com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor implements InnerInterceptor {

    public TenantLineInnerInterceptor(T tenantLineHandler) {
        super(tenantLineHandler);
    }

    protected void processInsert(Insert insert, int index, String sql, Object obj) {
        String tableName = insert.getTable().getName();
        T tenantLineHandler = getThisTenantLineHandler();
        // 忽略租户控制退出执行
        if (tenantLineHandler.ignoreTable(tableName)) {
            return;
        }

        List<Column> columns = insert.getColumns();
        // 针对不给列名的insert 不处理
        if (CollUtil.isEmpty(columns)) {
            return;
        }

        // 增加租户列
        String tenantIdColumn = tenantLineHandler.getTenantIdColumn();
        columns.add(new Column(tenantIdColumn));
        Expression tenantId = tenantLineHandler.getInsertTenantId(tableName);
        List<UpdateSet> duplicateUpdateColumns = insert.getDuplicateUpdateSets();
        if (CollUtil.isNotEmpty(duplicateUpdateColumns)) {
            duplicateUpdateColumns.add(new UpdateSet(new Column(tenantIdColumn), tenantId));
        }

        Select select = insert.getSelect();
        if (select instanceof PlainSelect) {
            processInsertSelect(select, (String) obj);
        } else {
            if (insert.getValues() == null) {
                throw ExceptionUtils.mpe("Failed to process multiple-table update, please exclude the tableName or statementId", new Object[0]);
            }

            Values values = insert.getValues();
            ExpressionList<Expression> expressions = (ExpressionList<Expression>) values.getExpressions();
            if (expressions instanceof ParenthesedExpressionList) {
                expressions.addExpression(tenantId);
            } else if (CollUtil.isNotEmpty(expressions)) {
                int len = expressions.size();

                for (int i = 0; i < len; ++i) {
                    Expression expression = expressions.get(i);
                    if (expression instanceof ParenthesedExpressionList) {
                        ((ParenthesedExpressionList) expression).addExpression(tenantId);
                    } else {
                        expressions.add(tenantId);
                    }
                }
            } else {
                expressions.add(tenantId);
            }
        }
    }

    public Expression buildTableExpression(final Table table, final Expression where, final String whereSegment) {
        return getThisTenantLineHandler().ignoreTable(table.getName()) ? null : getThisTenantLineHandler().updateExpression(table, where);
    }

    /**
     * 获取当前定义TenantLineHandler
     */
    public T getThisTenantLineHandler() {
        return (T) super.getTenantLineHandler();
    }
}
