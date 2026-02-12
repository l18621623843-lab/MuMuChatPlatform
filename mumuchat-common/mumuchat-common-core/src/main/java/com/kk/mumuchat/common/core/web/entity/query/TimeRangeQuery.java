package com.kk.mumuchat.common.core.web.entity.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 时间范围 查询类
 *
 * @author xueyi
 */
@Data
@NoArgsConstructor
public class TimeRangeQuery implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 起始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime beginTime;

    /** 终止时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime endTime;

    /** 排序（true/降序 false/升序 null不排序） */
    private Boolean desc;

    /** 起始时间是否包含等于条件 */
    private Boolean beginTimeEqual = Boolean.TRUE;

    /** 终止时间是否包含等于条件 */
    private Boolean endTimeEqual = Boolean.TRUE;

    public TimeRangeQuery(LocalDateTime beginTime, LocalDateTime endTime) {
        this(beginTime, endTime, null);
    }

    public TimeRangeQuery(Boolean desc) {
        this(null, null, desc);
    }

    public TimeRangeQuery(LocalDateTime beginTime, LocalDateTime endTime, Boolean desc) {
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.desc = desc;
    }
}
