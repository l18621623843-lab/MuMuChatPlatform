package com.kk.mumuchat.system.utils.V2;

import com.kk.mumuchat.common.core.constant.basic.DictConstants;
import com.kk.mumuchat.common.core.utils.TreeUtil;
import com.kk.mumuchat.common.core.utils.core.CollUtil;
import com.kk.mumuchat.common.core.utils.core.ObjectUtil;
import com.kk.mumuchat.common.core.utils.core.StrUtil;
import com.kk.mumuchat.system.api.authority.constant.AuthorityConstants;
import com.kk.mumuchat.system.api.authority.domain.dto.SysMenuDto;
import com.kk.mumuchat.system.utils.V2.constant.RouteV2Constants;
import com.kk.mumuchat.system.utils.V2.route.MMetaVo;
import com.kk.mumuchat.system.utils.V2.route.MRouterVo;
import com.kk.mumuchat.system.utils.V2.route.MTagVo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 路由树工具类
 *
 * @author xueyi
 */
public class MRouteUtils {

    private static final int DYNAMIC_LEVEL = 5;

    /** 路由树初始深度 */
    private static final int LEVEL_0 = 0;

    /**
     * 构建前端路由所需要的菜单
     *
     * @param menus 菜单列表
     * @return 路由列表
     */
    public static List<MRouterVo> buildMenus(List<SysMenuDto> menus) {
        SysMenuDto menu = new SysMenuDto();
        menu.setFullPath(StrUtil.EMPTY);
        menu.setChildren(menus);
        return recursionFn(menu, LEVEL_0);
    }

    /**
     * 构建模块路由路径映射Map
     *
     * @param menuList 菜单信息对象集合
     * @return 模块路由路径映射Map
     */
    public static Map<String, String> buildMenuRouteMap(List<SysMenuDto> menuList) {
        return buildRoutePath(menuList.stream().filter(menu -> (menu.isDir() || menu.isMenu() || menu.isDetails()))
                .filter(item -> StrUtil.isBlank(item.getVersion()))
                .collect(Collectors.toList()));
    }

    /**
     * 递归菜单列表
     *
     * @param menus 菜单列表
     * @param level 路由树深度
     * @return 路由树
     */
    private static List<MRouterVo> recursionFn(SysMenuDto menus, int level) {
        List<MRouterVo> routers = new ArrayList<>();
        if (CollUtil.isNotEmpty(menus.getChildren())) {
            MRouterVo router;
            for (SysMenuDto menu : menus.getChildren()) {
                if (level == LEVEL_0 && menu.isDetails()) {
                    router = new MRouterVo();
                    getRoute(menu, router);
                    routers.add(router);
                }
                menu.setFullPath(menus.getFullPath() + StrUtil.SLASH + menu.getPath());
                if (CollUtil.isNotEmpty(menu.getChildren()))
                    assembleDetails(menu, routers);
                if (!menu.isDetails()) {
                    router = new MRouterVo();
                    if (CollUtil.isNotEmpty(menu.getChildren())) {
                        router.setChildren(recursionFn(menu, ++level));
                    }
                    getRoute(menu, router);
                    routers.add(router);
                }
            }
        }
        return routers;
    }

    /**
     * 组装详情列表
     *
     * @param menu    菜单对象
     * @param routers 路由列表
     */
    private static void assembleDetails(SysMenuDto menu, List<MRouterVo> routers) {
        MRouterVo router;
        for (SysMenuDto detailsMenu : menu.getChildren()) {
            if (detailsMenu.isDetails()) {
                detailsMenu.setCurrentActiveMenu(menu.getFullPath());
                router = new MRouterVo();
                // 详情型菜单上移一级
                detailsMenu.setParentId(menu.getParentId());
                getRoute(detailsMenu, router);
                routers.add(router);
            }
        }
    }

    /**
     * 获取路由信息
     *
     * @param menu   菜单信息
     * @param router 路由信息
     */
    private static void getRoute(SysMenuDto menu, MRouterVo router) {
        router.setMeta(getMeta(menu));
        router.setPath(getRouterPath(menu));
        router.setName(menu.getName());
        router.setDisabled(StrUtil.equals(DictConstants.DicYesNo.YES.getCode(), menu.getIsDisabled()));
        router.setComponent(getComponent(menu));
    }

    /**
     * 获取菜单标签信息
     *
     * @param menu 菜单信息
     * @return 菜单标签信息
     */
    private static MTagVo getTag(SysMenuDto menu) {
        return new MTagVo();
    }

    /**
     * 获取路由元信息
     *
     * @param menu 菜单信息
     * @return 路由元信息
     */
    private static MMetaVo getMeta(SysMenuDto menu) {
        MMetaVo meta = new MMetaVo();
        meta.setTitle(menu.getTitle());
        meta.setIcon(menu.getIcon());
        if (menu.isDetails()) {
            meta.setDynamicLevel(DYNAMIC_LEVEL);
            meta.setCurrentActiveMenu(menu.getCurrentActiveMenu());
        }
        meta.setIgnoreKeepAlive(StrUtil.equals(DictConstants.DicYesNo.YES.getCode(), menu.getIsCache()));
        meta.setAffix(StrUtil.equals(DictConstants.DicYesNo.YES.getCode(), menu.getIsAffix()));
        if (menu.isEmbedded()) {
            meta.setFrameSrc(menu.getFrameSrc());
        }
        meta.setTransitionName(menu.getTransitionName());
        meta.setHideBreadcrumb(StrUtil.equals(DictConstants.DicShowHide.HIDE.getCode(), menu.getHideBreadcrumb()));
        if (StrUtil.isNotEmpty(menu.getParamPath())) {
            meta.setCarryParam(true);
        }
        meta.setHideTab(StrUtil.equals(DictConstants.DicShowHide.HIDE.getCode(), menu.getHideTab()));
        meta.setHideMenu(StrUtil.equals(DictConstants.DicShowHide.HIDE.getCode(), menu.getHideMenu()));
        meta.setHideChildrenInMenu(StrUtil.equals(DictConstants.DicShowHide.HIDE.getCode(), menu.getHideChildren()));
        if (menu.isExternalLinks()) {
            meta.setIsLink(true);
        }
        meta.setOrderNo(menu.getSort());
        meta.setParams(menu.getParamPath());
        meta.setIgnoreRoute(StrUtil.equals(DictConstants.DicYesNo.YES.getCode(), menu.getIgnoreRoute()));
        meta.setHidePathForChildren(StrUtil.equals(DictConstants.DicShowHide.HIDE.getCode(), menu.getHidePathForChildren()));
        return meta;
    }

    /**
     * 获取路由地址
     *
     * @param menu 菜单信息
     * @return 路由地址
     */
    private static String getRouterPath(SysMenuDto menu) {
        // 外链方式
        if (StrUtil.equals(AuthorityConstants.FrameType.EXTERNAL_LINKS.getCode(), menu.getFrameType())) {
            return menu.getFrameSrc();
        }
        // 一级目录
        else if (ObjectUtil.equals(AuthorityConstants.MENU_TOP_NODE, menu.getParentId())) {
            return StrUtil.SLASH + menu.getPath();
        }
        return menu.getPath();
    }

    /**
     * 获取组件信息
     *
     * @param menu 菜单信息
     * @return 组件信息
     */
    private static String getComponent(SysMenuDto menu) {
        return menu.isEmbedded() || menu.isExternalLinks()
                ? RouteV2Constants.ComponentType.IFRAME.getCode()
                : ObjectUtil.equals(AuthorityConstants.MENU_TOP_NODE, menu.getParentId()) && !(menu.isMenu() || menu.isDetails())
                ? RouteV2Constants.ComponentType.LAYOUT.getCode()
                : menu.getComponent();
    }

    /**
     * 模块路由构建|构建路由路径集合
     *
     * @param menus 菜单列表
     * @return 路径集合
     */
    private static Map<String, String> buildRoutePath(List<SysMenuDto> menus) {
        Map<String, String> routeMap = new HashMap<>();
        if (CollUtil.isEmpty(menus))
            return new HashMap<>();
        List<SysMenuDto> menuTree = TreeUtil.buildTree(menus);
        SysMenuDto menu = new SysMenuDto();
        menu.setFullPath(StrUtil.EMPTY);
        menu.setChildren(menuTree);
        menuTree.forEach(sonChild -> {
            if (sonChild.isDetails()) {
                routeMap.put(sonChild.getName(), StrUtil.SLASH + sonChild.getPath());
            }
        });
        recursionFn(menu, routeMap);
        return routeMap;
    }

    /**
     * 模块路由构建|递归菜单树
     *
     * @param menu     菜单对象
     * @param routeMap 路径集合
     */
    private static void recursionFn(SysMenuDto menu, Map<String, String> routeMap) {
        if (CollUtil.isNotEmpty(menu.getChildren())) {
            menu.getChildren().forEach(sonChild -> {
                sonChild.setFullPath(menu.getFullPath() + StrUtil.SLASH + sonChild.getPath());
                if (!sonChild.isDetails())
                    routeMap.put(sonChild.getName(), sonChild.getFullPath());
                if (CollUtil.isNotEmpty(sonChild.getChildren())) {
                    sonChild.getChildren().forEach(grandsonChild -> {
                        if (grandsonChild.isDetails()) {
                            String detailsSuffix = grandsonChild.getDetailsSuffix();
                            grandsonChild.setFullPath(StrUtil.isNotEmpty(detailsSuffix) ? menu.getFullPath() + StrUtil.SLASH + detailsSuffix : menu.getFullPath());
                            routeMap.put(grandsonChild.getName(), grandsonChild.getFullPath());
                        }
                    });
                    recursionFn(sonChild, routeMap);
                }
            });
        }
    }
}
