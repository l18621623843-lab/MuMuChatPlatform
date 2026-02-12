package com.kk.mumuchat.system.api.authority.domain.po.meta;

import com.alibaba.fastjson2.JSONObject;
import lombok.Data;

import java.util.List;

/**
 * 菜单路由元数据对象
 *
 * @author xueyi
 */
@Data
public class SysMenuMetaPo {

    /** auto | 标题名称, 设置该路由在侧边栏和面包屑中展示的名字 */
    private String title;

    /** 图标（菜单/tab） */
    private String icon;

    /** 激活图标（菜单） */
    private String activeIcon;

    /** 固定标签页的顺序 */
    private Integer affixTabOrder;

    /** 需要特定的角色标识才可以访问 */
    private List<String> authority;

    /** 徽标 */
    private String badge;

    /** 徽标类型 */
    private String badgeType;

    /** 徽标颜色 */
    private String badgeVariants;

    /** 菜单可以看到，但是访问会被重定向到403 */
    private Boolean menuVisibleWithForbidden;

    /** 当前路由不使用基础布局（仅在顶级生效） */
    private Boolean noBasicLayout;

    /** 菜单所携带的参数 */
    private JSONObject query;

    /** 菜单所携带的隐藏参数 */
    private JSONObject params;

    /** 在新窗口打开 */
    private Boolean openInNewWindow;

}
