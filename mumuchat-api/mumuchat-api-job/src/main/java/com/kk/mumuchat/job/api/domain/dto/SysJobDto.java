package com.kk.mumuchat.job.api.domain.dto;

import com.kk.mumuchat.job.api.domain.po.SysJobPo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.util.List;

/**
 * 调度任务 数据传输对象
 *
 * @author mumuchat
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysJobDto extends SysJobPo {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 任务执行结果 */
    private String result;

    /** 任务记录数据集合 */
    private List<SysJobLogDto> subList;
}
