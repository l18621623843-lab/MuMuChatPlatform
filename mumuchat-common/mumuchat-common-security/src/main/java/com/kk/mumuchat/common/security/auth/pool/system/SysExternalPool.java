package com.kk.mumuchat.common.security.auth.pool.system;

/**
 * 系统服务 | 第三方模块 权限标识常量
 *
 * @author xueyi
 */
public interface SysExternalPool {

    /** 系统服务 | 第三方模块 | 外系统管理 | 列表 */
    String SYS_EXTERNAL_SYSTEM_LIST = "RD:system:external:external:list";
    /** 系统服务 | 第三方模块 | 外系统管理 | 详情 */
    String SYS_EXTERNAL_SYSTEM_SINGLE = "RD:system:external:external:single";
    /** 系统服务 | 第三方模块 | 外系统管理 | 新增 */
    String SYS_EXTERNAL_SYSTEM_ADD = "RD:system:external:external:add";
    /** 系统服务 | 第三方模块 | 外系统管理 | 修改 */
    String SYS_EXTERNAL_SYSTEM_EDIT = "RD:system:external:external:edit";
    /** 系统服务 | 第三方模块 | 外系统管理 | 状态修改 */
    String SYS_EXTERNAL_SYSTEM_ES = "RD:system:external:external:es";
    /** 系统服务 | 第三方模块 | 外系统管理 | 删除 */
    String SYS_EXTERNAL_SYSTEM_DEL = "RD:system:external:external:del";
    /** 系统服务 | 第三方模块 | 外系统管理 | 缓存 */
    String SYS_EXTERNAL_SYSTEM_CACHE = "RD:system:external:external:cache";
}
