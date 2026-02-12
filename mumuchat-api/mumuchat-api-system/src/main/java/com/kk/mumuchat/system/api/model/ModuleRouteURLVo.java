package com.kk.mumuchat.system.api.model;

import lombok.Data;

/**
 * 模块路由映射信息 数据输出对象
 *
 * @author xueyi
 */
@Data
public class ModuleRouteURLVo {

    /** 模块Id */
    private String moduleId;

    /** 菜单路由 */
    private String routeURL;
}
