package com.kk.mumuchat.common.core.web.page;

import com.kk.mumuchat.common.core.utils.core.ObjectUtil;
import com.kk.mumuchat.common.core.utils.core.StrUtil;
import lombok.Data;

/**
 * 分页数据
 *
 * @author xueyi
 */
@Data
public class PageDomain {

    /** 当前记录起始索引 */
    private Integer pageNum;

    /** 每页显示记录数 */
    private Integer pageSize;

    /** 排序列 */
    private String orderByColumn;

    /** 排序的方向desc或者asc */
    private String isAsc = "asc";

    /** 分页参数合理化 */
    private Boolean reasonable = false;

    public String getOrderBy() {
        if (StrUtil.isEmpty(orderByColumn)) {
            return StrUtil.EMPTY;
        }
        return StrUtil.toUnderlineCase(orderByColumn) + StrUtil.SPACE + isAsc;
    }

    public void setIsAsc(String isAsc) {
        if (StrUtil.isNotEmpty(isAsc)) {
            // 兼容前端排序类型
            if ("ascending".equals(isAsc)) {
                isAsc = "asc";
            } else if ("descending".equals(isAsc)) {
                isAsc = "desc";
            }
            this.isAsc = isAsc;
        }
    }

    public Boolean getReasonable() {
        if (ObjectUtil.isNull(reasonable)) {
            return Boolean.FALSE;
        }
        return reasonable;
    }
}