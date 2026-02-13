package com.kk.mumuchat.system.api.dict.domain.query;

import com.kk.mumuchat.system.api.dict.domain.po.SysImExPo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 导入导出配置 数据查询对象
 *
 * @author mumuchat
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysImExQuery extends SysImExPo {

    @Serial
    private static final long serialVersionUID = 1L;
}