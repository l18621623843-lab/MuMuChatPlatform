package com.kk.mumuchat.common.core.web.page;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 表格分页数据对象
 *
 * @author mumuchat
 */
@Data
@NoArgsConstructor
public class TableDataInfo<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 总记录数 */
    private long total;

    /** 列表数据 */
    private List<T> items;

    public TableDataInfo(long total) {
        this.total = total;
    }

    public TableDataInfo(List<T> list, int total) {
        this.items = list;
        this.total = total;
    }

    public TableDataInfo(List<T> list, long total) {
        this.items = list;
        this.total = total;
    }

    public static <T> TableDataInfo<T> empty(long total) {
        return new TableDataInfo<>(total);
    }
}