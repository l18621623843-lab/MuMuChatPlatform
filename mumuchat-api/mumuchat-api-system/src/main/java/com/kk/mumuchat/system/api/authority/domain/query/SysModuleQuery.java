package com.kk.mumuchat.system.api.authority.domain.query;

import com.kk.mumuchat.system.api.authority.domain.po.SysModulePo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 系统服务|权限模块|模块 数据查询对象
 *
 * @author mumuchat
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysModuleQuery extends SysModulePo {

    @Serial
    private static final long serialVersionUID = 1L;

}
