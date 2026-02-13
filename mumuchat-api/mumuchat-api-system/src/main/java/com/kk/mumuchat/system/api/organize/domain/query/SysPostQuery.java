package com.kk.mumuchat.system.api.organize.domain.query;

import com.kk.mumuchat.system.api.organize.domain.po.SysPostPo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 系统服务|组织模块|岗位 数据查询对象
 *
 * @author mumuchat
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysPostQuery extends SysPostPo {

    @Serial
    private static final long serialVersionUID = 1L;

}
