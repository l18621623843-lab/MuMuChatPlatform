package com.kk.mumuchat.system.utils.V5.route;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

/**
 * 路由配置信息
 *
 * @author mumuchat
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RouterVo {

    /** 路由名字 */
    private String name;

    /** 路由地址 */
    private String path;

    /** 重定向地址，当设置 noRedirect 的时候该路由在面包屑导航中不可被点击 */
    private String redirect;

    /** 组件地址 */
    private String component;

    /** 是否禁用 */
    private Boolean disabled;

    /** 其他元素 */
    private MetaVo meta;

    /** 子路由 */
    private List<RouterVo> children;

}
