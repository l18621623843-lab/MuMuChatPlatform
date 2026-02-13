package com.kk.mumuchat.system.utils.V5;

import com.alibaba.fastjson2.JSONObject;
import com.kk.mumuchat.common.core.constant.basic.DictConstants;
import com.kk.mumuchat.common.core.utils.TreeUtil;
import com.kk.mumuchat.common.core.utils.core.BooleanUtil;
import com.kk.mumuchat.common.core.utils.core.CollUtil;
import com.kk.mumuchat.common.core.utils.core.IdUtil;
import com.kk.mumuchat.common.core.utils.core.ObjectUtil;
import com.kk.mumuchat.common.core.utils.core.SpringUtil;
import com.kk.mumuchat.common.core.utils.core.StrUtil;
import com.kk.mumuchat.system.api.authority.constant.AuthorityConstants;
import com.kk.mumuchat.system.api.authority.domain.dto.SysMenuDto;
import com.kk.mumuchat.system.api.model.ModuleRouteURLVo;
import com.kk.mumuchat.system.utils.V5.constant.RouteConstants;
import com.kk.mumuchat.system.utils.V5.model.SysRouteConverter;
import com.kk.mumuchat.system.utils.V5.route.MetaVo;
import com.kk.mumuchat.system.utils.V5.route.RouterVo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 路由树工具类
 *
 * @author mumuchat
 */
public class RouteUtil {

    private static SysRouteConverter routeConverter;

    private static final int DYNAMIC_LEVEL = 5;

    /** 路由树初始深度 */
    private static final int LEVEL_0 = 0;

    private static SysRouteConverter getRouteConverter() {
        if (routeConverter == null)
            routeConverter = SpringUtil.getBean(SysRouteConverter.class);
        return routeConverter;
    }

    /**
     * 构建前端路由所需要的菜单
     *
     * @param menus 菜单列表
     * @return 路由列表
     */
    public static List<RouterVo> buildMenus(List<SysMenuDto> menus) {
        SysMenuDto menu = new SysMenuDto();
        menu.setFullPath(StrUtil.EMPTY);
        menu.setChildren(menus);

        List<RouterVo> topRouters = new ArrayList<>();
        List<RouterVo> routerTree = recursionFn(menu, LEVEL_0, topRouters);
        // 判断是否存在特定顶级路由
        if (CollUtil.isNotEmpty(topRouters)) {
            routerTree.addAll(topRouters);
        }
        return routerTree;
    }

    /**
     * 构建模块路由路径映射Map
     *
     * @param menuList 菜单信息对象集合
     * @return 模块路由路径映射Map
     */
    public static Map<String, ModuleRouteURLVo> buildMenuRouteMap(List<SysMenuDto> menuList){
        return buildRoutePath(menuList.stream().filter(menu -> (menu.isDir() || menu.isMenu() || menu.isDetails()))
                .collect(Collectors.toList()));
    }

    /**
     * 递归菜单列表
     *
     * @param menus      菜单列表
     * @param level      路由树深度
     * @param topRouters 顶级路由表
     * @return 路由树
     */
    private static List<RouterVo> recursionFn(SysMenuDto menus, int level, List<RouterVo> topRouters) {
        List<RouterVo> routers = new ArrayList<>();
        if (CollUtil.isNotEmpty(menus.getChildren())) {
            RouterVo router;
            for (SysMenuDto menu : menus.getChildren()) {
                if (level == LEVEL_0 && menu.isDetails()) {
                    router = new RouterVo();
                    getRoute(menu, router);
                    addToRouter(router, routers, topRouters);
                }
                StrUtil.setStrIfBlank(menu.getFullPath(), () -> {
                    String currentPath = StrUtil.isNotBlank(menu.getPath()) ? StrUtil.SLASH + menu.getPath() : StrUtil.EMPTY;
                    menu.setFullPath(menus.getFullPath() + currentPath);
                });
                if (CollUtil.isNotEmpty(menu.getChildren())) {
                    assembleDetails(menu, routers, topRouters);
                }
                if (!menu.isDetails()) {
                    router = new RouterVo();
                    if (CollUtil.isNotEmpty(menu.getChildren())) {
                        router.setChildren(recursionFn(menu, ++level, topRouters));
                    }
                    getRoute(menu, router);
                    addToRouter(router, routers, topRouters);
                }
            }
        }
        return routers;
    }

    /**
     * 添加当前路由至路由树中
     *
     * @param currentRouter    待添加路由对象
     * @param thisLevelRouters 本层级路由表
     * @param topRouters       顶级路由表
     */
    private static void addToRouter(RouterVo currentRouter, List<RouterVo> thisLevelRouters, List<RouterVo> topRouters) {
        Optional.ofNullable(currentRouter).map(RouterVo::getMeta)
                .filter(item -> BooleanUtil.isTrue(item.getNoBasicLayout()))
                .ifPresentOrElse(item -> topRouters.add(currentRouter),
                        () -> thisLevelRouters.add(currentRouter));
    }

    /**
     * 组装详情列表
     *
     * @param menu       菜单对象
     * @param routers    路由列表
     * @param topRouters 顶级路由表
     */
    private static void assembleDetails(SysMenuDto menu, List<RouterVo> routers, List<RouterVo> topRouters) {
        RouterVo router;
        for (SysMenuDto detailsMenu : menu.getChildren()) {
            if (detailsMenu.isDetails()) {
                String detailsSuffix = detailsMenu.getDetailsSuffix();
                if (StrUtil.isNotBlank(detailsSuffix) && detailsSuffix.endsWith(StrUtil.SLASH)) {
                    detailsSuffix = detailsSuffix.substring(0, detailsSuffix.length() - 1);
                }
                String parentFullPath = menu.getFullPath();
                String parentPath = StrUtil.SLASH + menu.getPath();
                if (parentFullPath.endsWith(parentPath)) {
                    parentFullPath = parentFullPath.substring(0, parentFullPath.length() - parentPath.length());
                }
                detailsMenu.setFullPath(StrUtil.isNotBlank(detailsSuffix) ? parentFullPath + StrUtil.SLASH + detailsSuffix : menu.getPath());
                detailsMenu.setCurrentActiveMenu(menu.getFullPath());
                router = new RouterVo();
                // 详情型菜单上移一级
                detailsMenu.setParentId(menu.getParentId());
                getRoute(detailsMenu, router);
                addToRouter(router, routers, topRouters);
            }
        }
    }

    /**
     * 获取路由信息
     *
     * @param menu   菜单信息
     * @param router 路由信息
     */
    private static void getRoute(SysMenuDto menu, RouterVo router) {
        MetaVo metaInfo = ObjectUtil.isNotNull(menu.getMetaInfo())
                ? getRouteConverter().mapperMeta(menu.getMetaInfo())
                : new MetaVo();
        router.setMeta(getMeta(metaInfo, menu));
        router.setPath(getRouterPath(menu));
        router.setName(menu.getName());
        router.setDisabled(StrUtil.equals(DictConstants.DicYesNo.YES.getCode(), menu.getIsDisabled()));
        router.setComponent(getComponent(menu));
    }

    /**
     * 获取路由元信息
     *
     * @param metaInfo 菜单元信息
     * @param menuInfo 菜单信息
     * @return 路由元信息
     */
    private static MetaVo getMeta(MetaVo metaInfo, SysMenuDto menuInfo) {
        ObjectUtil.setObjIfNull(metaInfo.getTitle(), () -> metaInfo.setTitle(menuInfo.getTitle()));
        ObjectUtil.setObjIfNull(metaInfo.getIcon(), () -> metaInfo.setIcon(menuInfo.getIcon()));
        ObjectUtil.setObjIfNull(metaInfo.getActiveIcon(), () -> metaInfo.setActiveIcon(menuInfo.getIcon()));
        if (menuInfo.isDetails()) {
            metaInfo.setMaxNumOfOpenTab(DYNAMIC_LEVEL);
            metaInfo.setActivePath(menuInfo.getCurrentActiveMenu());
        }
        metaInfo.setKeepAlive(StrUtil.equals(DictConstants.DicYesNo.YES.getCode(), menuInfo.getIsCache()));
        metaInfo.setAffixTab(StrUtil.equals(DictConstants.DicYesNo.YES.getCode(), menuInfo.getIsAffix()));
        if (menuInfo.isEmbedded()) {
            metaInfo.setIframeSrc(menuInfo.getFrameSrc());
        } else if (menuInfo.isExternalLinks()) {
            metaInfo.setLink(menuInfo.getFrameSrc());
        }
        metaInfo.setHideInBreadcrumb(StrUtil.equals(DictConstants.DicShowHide.HIDE.getCode(), menuInfo.getHideBreadcrumb()));
        metaInfo.setHideInTab(StrUtil.equals(DictConstants.DicShowHide.HIDE.getCode(), menuInfo.getHideTab()));
        metaInfo.setHideInMenu(StrUtil.equals(DictConstants.DicShowHide.HIDE.getCode(), menuInfo.getHideMenu()));
        metaInfo.setHideChildrenInMenu(StrUtil.equals(DictConstants.DicShowHide.HIDE.getCode(), menuInfo.getHideChildren()));
        // 忽略权限
        ObjectUtil.setObjIfNull(metaInfo.getIgnoreAccess(), () -> metaInfo.setIgnoreAccess(Boolean.TRUE));
        // 兼容旧版本菜单参数
        metaInfo.setTransitionName(menuInfo.getTransitionName());
        if (ObjectUtil.isNull(metaInfo.getParams())) {
            StrUtil.setStrIfNotBlank(menuInfo.getParamPath(), () -> metaInfo.setParams(JSONObject.parse(menuInfo.getParamPath())));
        }
        metaInfo.setOrder(menuInfo.getSort());
        // 菜单传输携带模块信息
        initModuleInfo(metaInfo, menuInfo);
        return metaInfo;
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
            return IdUtil.fastSimpleUUID();
        }
        String fullPath = menu.getFullPath();
        if (StrUtil.isNotBlank(fullPath) && fullPath.endsWith("/:id")) {
            fullPath = fullPath.substring(0, fullPath.length() - 3); // 移除 "/:id"
        }
        return fullPath;
    }

    /**
     * 获取组件信息
     *
     * @param menu 菜单信息
     * @return 组件信息
     */
    private static String getComponent(SysMenuDto menu) {
        return menu.isEmbedded() || menu.isExternalLinks()
                ? RouteConstants.ComponentType.IFRAME.getCode()
                : menu.getComponent();
    }

    /**
     * 菜单模块信息初始化
     *
     * @param metaInfo 元信息
     * @param menuInfo 菜单信息
     */
    private static void initModuleInfo(MetaVo metaInfo, SysMenuDto menuInfo) {
        JSONObject query = ObjectUtil.getObjOrNullElse(metaInfo.getQuery(), new JSONObject());
        query.put(AuthorityConstants.RouteField.MODULE.getCode(), menuInfo.getModuleId());
        metaInfo.setQuery(query);
    }

    /**
     * 模块路由构建|构建路由路径集合
     *
     * @param menus 菜单列表
     * @return 路径集合
     */
    private static Map<String, ModuleRouteURLVo> buildRoutePath(List<SysMenuDto> menus) {
        Map<String, ModuleRouteURLVo> routeMap = new HashMap<>();
        if (CollUtil.isEmpty(menus))
            return new HashMap<>();
        List<SysMenuDto> menuTree = TreeUtil.buildTree(menus);
        SysMenuDto menu = new SysMenuDto();
        menu.setFullPath(StrUtil.EMPTY);
        menu.setChildren(menuTree);
        menuTree.forEach(sonChild -> {
            if (sonChild.isDetails()) {
                ModuleRouteURLVo urlInfo = new ModuleRouteURLVo();
                urlInfo.setModuleId(sonChild.getModuleId().toString());
                urlInfo.setRouteURL(StrUtil.SLASH + sonChild.getPath());
                routeMap.put(sonChild.getName(), urlInfo);
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
    private static void recursionFn(SysMenuDto menu, Map<String, ModuleRouteURLVo> routeMap) {
        if (CollUtil.isNotEmpty(menu.getChildren())) {
            menu.getChildren().forEach(sonChild -> {
                sonChild.setFullPath(menu.getFullPath() + StrUtil.SLASH + sonChild.getPath());
                if (!sonChild.isDetails()) {
                    ModuleRouteURLVo urlInfo = new ModuleRouteURLVo();
                    urlInfo.setModuleId(sonChild.getModuleId().toString());
                    urlInfo.setRouteURL(sonChild.getFullPath());
                    routeMap.put(sonChild.getName(), urlInfo);
                }
                if (CollUtil.isNotEmpty(sonChild.getChildren())) {
                    sonChild.getChildren().forEach(grandsonChild -> {
                        if (grandsonChild.isDetails()) {
                            String detailsSuffix = grandsonChild.getDetailsSuffix();
                            grandsonChild.setFullPath(StrUtil.isNotEmpty(detailsSuffix) ? menu.getFullPath() + StrUtil.SLASH + detailsSuffix : menu.getFullPath());

                            ModuleRouteURLVo urlInfo = new ModuleRouteURLVo();
                            urlInfo.setModuleId(grandsonChild.getModuleId().toString());
                            urlInfo.setRouteURL(grandsonChild.getFullPath());
                            routeMap.put(grandsonChild.getName(), urlInfo);
                        }
                    });
                    recursionFn(sonChild, routeMap);
                }
            });
        }
    }
}
