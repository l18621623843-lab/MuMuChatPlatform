package com.kk.mumuchat.common.core.utils.sql;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.kk.mumuchat.common.core.exception.UtilException;
import com.kk.mumuchat.common.core.utils.core.BooleanUtil;
import com.kk.mumuchat.common.core.utils.core.ObjectUtil;
import com.kk.mumuchat.common.core.utils.core.StrUtil;
import com.kk.mumuchat.common.core.web.entity.base.BaseEntity;
import com.kk.mumuchat.common.core.web.entity.query.TimeRangeQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * sql操作工具类
 *
 * @author xueyi
 */
public class SqlUtil {

    /** 定义常用的 sql关键字 */
    public static String SQL_REGEX = "select |insert |delete |update |drop |count |exec |chr |mid |master |truncate |char |and |declare ";

    /** 仅支持字母、数字、下划线、空格、逗号、小数点（支持多个字段排序） */
    public static String SQL_PATTERN = "[a-zA-Z0-9_\\ \\,\\.]+";

    /** 限制orderBy最大长度 */
    private static final int ORDER_BY_MAX_LENGTH = 500;

    /**
     * 检查字符，防止注入绕过
     */
    public static String escapeOrderBySql(String value) {
        if (StrUtil.isNotEmpty(value) && !isValidOrderBySql(value)) {
            throw new UtilException("参数不符合规范，不能进行查询");
        }
        if (StrUtil.length(value) > ORDER_BY_MAX_LENGTH) {
            throw new UtilException("参数已超过最大限制，不能进行查询");
        }
        return value;
    }

    /**
     * 验证 order by 语法是否符合规范
     */
    public static boolean isValidOrderBySql(String value) {
        return value.matches(SQL_PATTERN);
    }


    /**
     * SQL关键字检查
     */
    public static void filterKeyword(String value) {
        if (StrUtil.isEmpty(value)) {
            return;
        }
        String[] sqlKeywords = StrUtil.splitToArray(SQL_REGEX, "\\|");
        for (String sqlKeyword : sqlKeywords) {
            if (StrUtil.indexOfIgnoreCase(value, sqlKeyword) > -1) {
                throw new UtilException("参数存在SQL注入风险");
            }
        }
    }

    /**
     * 处理时间范围查询条件
     *
     * @param queryWrapper 查询条件构造器
     * @param timeQuery    时间范围查询对象
     * @param columnFunc   字段函数
     */
    public static  <P extends BaseEntity> void handleTimeRangeQuery(LambdaQueryWrapper<P> queryWrapper, TimeRangeQuery timeQuery, SFunction<P, ?> columnFunc) {
        // 如果时间查询对象为空，则直接返回
        if(ObjectUtil.isNull(timeQuery)) {
            return;
        }

        // 处理起始时间查询条件
        if(ObjectUtil.isNotNull(timeQuery.getBeginTime())) {
            // 根据beginTimeEqual字段决定使用大于还是大于等于操作符
            if(BooleanUtil.isFalse(timeQuery.getBeginTimeEqual())) {
                queryWrapper.gt(columnFunc, timeQuery.getBeginTime());
            }else {
                queryWrapper.ge(columnFunc, timeQuery.getBeginTime());
            }
        }

        // 处理终止时间查询条件
        if(ObjectUtil.isNotNull(timeQuery.getEndTime())) {
            // 根据endTimeEqual字段决定使用小于还是小于等于操作符
            if(BooleanUtil.isFalse(timeQuery.getEndTimeEqual())) {
                queryWrapper.lt(columnFunc, timeQuery.getEndTime());
            }else {
                queryWrapper.le(columnFunc, timeQuery.getEndTime());
            }
        }

        // 处理排序条件
        if (ObjectUtil.isNotNull(timeQuery.getDesc())) {
            // 创建排序字段列表，包含时间字段和ID字段
            List<SFunction<P, ?>> columns = new ArrayList<>() {{
                add(columnFunc);
                add(P::getId);
            }};
            // 根据desc字段决定排序方式
            if(BooleanUtil.isTrue(timeQuery.getDesc())) {
                queryWrapper.orderByDesc(columns);
            } else {
                queryWrapper.orderByAsc(columns);
            }
        }
    }
}
