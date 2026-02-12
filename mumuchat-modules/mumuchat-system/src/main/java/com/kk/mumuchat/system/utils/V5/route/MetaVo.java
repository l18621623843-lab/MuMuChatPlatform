package com.kk.mumuchat.system.utils.V5.route;

import com.kk.mumuchat.system.api.authority.domain.po.meta.SysMenuMetaPo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 路由元信息
 *
 * @author xueyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MetaVo extends SysMenuMetaPo {

    /** auto | 用于路由->菜单排序 */
    private Integer order;

    /** 指定该路由切换的动画名 */
    @Deprecated
    private String transitionName;

    /** auto | 当前激活的菜单，有时候不想激活现有菜单，需要激活父级菜单时使用 */
    private String activePath;

    /** auto | 是否固定标签页 */
    private Boolean affixTab;

    /** auto | 当前路由的子级在菜单中不展现 */
    private Boolean hideChildrenInMenu;

    /** auto | 标签页最大打开数量 */
    private Integer maxNumOfOpenTab;

    /** auto | 当前路由在面包屑中不展现 */
    private Boolean hideInBreadcrumb;

    /** auto | 当前路由在菜单中不展现 */
    private Boolean hideInMenu;

    /** auto | 当前路由在标签页不展现 */
    private Boolean hideInTab;

    /** auto | 忽略权限，直接可以访问 */
    private Boolean ignoreAccess;

    /** auto | 开启KeepAlive缓存 */
    private Boolean keepAlive;

    /** 路由是否已经加载过 */
    private Boolean loaded;

    /** auto | IFrame 地址 */
    private String iframeSrc;

    /** auto | 外链-跳转路径 */
    private String link;

}
