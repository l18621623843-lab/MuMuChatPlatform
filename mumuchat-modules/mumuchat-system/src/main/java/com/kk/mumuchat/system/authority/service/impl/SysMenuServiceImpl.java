package com.kk.mumuchat.system.authority.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import com.kk.mumuchat.common.core.constant.basic.BaseConstants;
import com.kk.mumuchat.common.core.constant.basic.OperateConstants;
import com.kk.mumuchat.common.core.constant.basic.SecurityConstants;
import com.kk.mumuchat.common.core.constant.basic.TenantConstants;
import com.kk.mumuchat.common.core.context.SecurityContextHolder;
import com.kk.mumuchat.common.core.exception.ServiceException;
import com.kk.mumuchat.common.core.utils.core.CollUtil;
import com.kk.mumuchat.common.core.utils.core.IdUtil;
import com.kk.mumuchat.common.core.utils.core.ObjectUtil;
import com.kk.mumuchat.common.core.utils.core.StrUtil;
import com.kk.mumuchat.common.core.utils.core.URLUtil;
import com.kk.mumuchat.common.security.service.TokenUserService;
import com.kk.mumuchat.common.security.utils.SecurityUserUtils;
import com.kk.mumuchat.common.web.correlate.contant.CorrelateConstants;
import com.kk.mumuchat.common.web.entity.service.impl.TreeServiceImpl;
import com.kk.mumuchat.system.api.authority.constant.AuthorityConstants;
import com.kk.mumuchat.system.api.authority.domain.dto.SysMenuDto;
import com.kk.mumuchat.system.api.authority.domain.dto.SysModuleDto;
import com.kk.mumuchat.system.api.authority.domain.query.SysMenuQuery;
import com.kk.mumuchat.system.api.model.DataScope;
import com.kk.mumuchat.system.api.model.LoginUser;
import com.kk.mumuchat.system.authority.domain.correlate.SysMenuCorrelate;
import com.kk.mumuchat.system.authority.manager.ISysMenuManager;
import com.kk.mumuchat.system.authority.service.ISysMenuService;
import com.kk.mumuchat.system.authority.service.ISysModuleService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 系统服务|权限模块|菜单管理 服务层处理
 *
 * @author xueyi
 */
@Service
public class SysMenuServiceImpl extends TreeServiceImpl<SysMenuQuery, SysMenuDto, SysMenuCorrelate, ISysMenuManager> implements ISysMenuService {

    @Resource
    private TokenUserService tokenService;

    @Resource
    protected ISysModuleService moduleService;

    /**
     * 默认方法关联配置定义
     */
    @Override
    protected Map<CorrelateConstants.ServiceType, SysMenuCorrelate> defaultCorrelate() {
        return new HashMap<>() {{
            put(CorrelateConstants.ServiceType.DELETE, SysMenuCorrelate.BASE_DEL);
        }};
    }

    /**
     * 查询菜单对象列表|数据权限|附加数据
     *
     * @param menu 菜单对象
     * @return 菜单对象集合
     */
    @Override
//    @DataScope(userAlias = CREATE_BY, mapperScope = {"SysMenuMapper"})
    public List<SysMenuDto> selectListScope(SysMenuQuery menu) {
        boolean isAdminTenant = SecurityUserUtils.isAdminTenant();
        return SecurityContextHolder.setTenantIgnoreFun(() -> {
            List<SysMenuDto> list = super.selectListScope(menu);
            subCorrelates(list, SysMenuCorrelate.EN_INFO_SELECT);
            subCorrelates(list, SysMenuCorrelate.INFO_LIST);
            return list;
        }, isAdminTenant);
    }

    @Override
    public SysMenuDto selectInfoById(Serializable id) {
        boolean isAdminTenant = SecurityUserUtils.isAdminTenant();
        return SecurityContextHolder.setTenantIgnoreFun(() -> {
            SysMenuDto menu = selectById(id);
            subCorrelates(menu, SysMenuCorrelate.EN_INFO_SELECT);
            subCorrelates(menu, SysMenuCorrelate.INFO_LIST);
            return menu;
        }, isAdminTenant);
    }

    @Override
    public List<SysMenuDto> getRoutes(Long moduleId, String version, Collection<Long> menuIds) {
        JSONObject params = new JSONObject();
        params.put(SecurityConstants.ENTERPRISE_ID, SecurityUserUtils.getEnterpriseId());
        params.put(SecurityConstants.ENTERPRISE_NAME, SecurityUserUtils.getEnterpriseName());
        List<SysMenuDto> menuList = CollUtil.selectListToBatch(menuIds,
                batchIds -> baseManager.getRoutes(moduleId, version, batchIds));
        // 处理菜单变量字段
        menuList.stream().filter(menu -> AuthorityConstants.MenuType.isMenu(menu.getMenuType()))
                .filter(menu -> StrUtil.isNotBlank(menu.getFrameSrc()))
                .forEach(menu -> {
                    String newFrameSrc = URLUtil.replaceTemplateVariables(menu.getFrameSrc(), params);
                    menu.setFrameSrc(newFrameSrc);
                });
        return menuList;
    }

    @Override
    public List<SysMenuDto> selectEnterpriseList(Set<Long> authGroupIds, Set<Long> roleIds, String isLessor, String userType) {
        return baseManager.selectEnterpriseList(authGroupIds, roleIds, isLessor, userType);
    }

    @Override
    public List<SysMenuDto> selectListToCurrentLoginUser(String version) {
        LoginUser loginUserInfo = tokenService.getLoginUser();
        DataScope dataScope = loginUserInfo.getDataScope();
        Set<Long> menuIds = dataScope.getMenuIds();
        return baseManager.selectMenuListByIdsWithVersion(menuIds, version);
    }

    @Override
    @DSTransactional
    public int insert(SysMenuDto menu) {
        menu.setName(IdUtil.simpleUUID());
        return super.insert(menu);
    }

    @Override
    @DSTransactional
    public int insertBatch(Collection<SysMenuDto> menuList) {
        if (CollUtil.isNotEmpty(menuList)) {
            menuList.forEach(menu -> menu.setName(IdUtil.simpleUUID()));
        }
        return super.insertBatch(menuList);
    }

    /**
     * 单条操作 - 开始处理
     *
     * @param operate 服务层 - 操作类型
     * @param newDto  新数据对象（删除时不存在）
     * @param id      Id集合（非删除时不存在）
     */
    @Override
    protected SysMenuDto startHandle(OperateConstants.ServiceType operate, SysMenuDto newDto, Serializable id) {
        SysMenuDto originDto = SecurityContextHolder.setTenantIgnoreFun(() -> super.startHandle(operate, newDto, id));
        switch (operate) {
            case ADD -> {
                if (newDto.isCommon()) {
                    newDto.setTenantId(TenantConstants.COMMON_TENANT_ID);
                } else if (ObjectUtil.isNull(newDto.getTenantId())) {
                    newDto.setTenantId(SecurityUserUtils.getEnterpriseId());
                }
            }
            case EDIT -> {
                if (ObjectUtil.isNull(originDto)) {
                    throw new ServiceException("数据不存在！");
                } else if (ObjectUtil.notEqual(originDto.getIsCommon(), newDto.getIsCommon())) {
                    throw new ServiceException(StrUtil.format("{}菜单{}失败，不允许变更公共类型！", operate.getInfo(), newDto.getName()));
                }
                newDto.setIsCommon(originDto.getIsCommon());
                newDto.setTenantId(originDto.getTenantId());
            }
            case EDIT_STATUS -> {
                newDto.setIsCommon(originDto.getIsCommon());
                newDto.setTenantId(originDto.getTenantId());
            }
            case DELETE -> {
                if (SecurityUserUtils.isNotAdminTenant() && (originDto.isCommon() || ObjectUtil.notEqual(originDto.getTenantId(), SecurityUserUtils.getEnterpriseId()))) {
                    throw new ServiceException("无操作权限，公共菜单不允许删除！");
                }
            }
        }

        switch (operate) {
            case ADD, EDIT -> {
                if (newDto.isCommon()) {
                    SysModuleDto module = SecurityContextHolder.setTenantIgnoreFun(() -> moduleService.selectById(newDto.getModuleId()));
                    if (ObjectUtil.isNull(module)) {
                        throw new ServiceException("数据不存在！");
                    }
                    if (module.isNotCommon()) {
                        throw new ServiceException(StrUtil.format("{}菜单{}失败，公共菜单必须挂载在公共模块下！", operate.getInfo(), newDto.getTitle()));
                    }

                    if (ObjectUtil.notEqual(BaseConstants.TOP_ID, newDto.getParentId())) {
                        SysMenuDto parentMenu = SecurityContextHolder.setTenantIgnoreFun(() -> baseManager.selectById(newDto.getParentId()));
                        if (ObjectUtil.isNull(parentMenu)) {
                            throw new ServiceException("数据不存在！");
                        }
                        if (parentMenu.isNotCommon()) {
                            throw new ServiceException(StrUtil.format("{}菜单{}失败，公共菜单必须挂载在公共菜单下！", operate.getInfo(), newDto.getTitle()));
                        }
                    }
                }
            }
        }

        switch (operate) {
            case ADD, EDIT, EDIT_STATUS -> {
                if (newDto.isNotCommon() && SecurityUserUtils.isNotAdminTenant() && ObjectUtil.notEqual(SecurityUserUtils.getEnterpriseId(), newDto.getTenantId())) {
                    throw new ServiceException(StrUtil.format("{}菜单{}失败，仅允许配置本企业私有菜单！", operate.getInfo(), newDto.getName()));
                }
                if (SecurityUserUtils.isAdminTenant()) {
                    SecurityContextHolder.setEnterpriseId(newDto.getTenantId().toString());
                }
            }
            case DELETE -> {
                if (SecurityUserUtils.isAdminTenant()) {
                    SecurityContextHolder.setTenantIgnore();
                }
            }
        }
        return originDto;
    }

    /**
     * 单条操作 - 结束处理
     *
     * @param operate   服务层 - 操作类型
     * @param row       操作数据条数
     * @param originDto 源数据对象（新增时不存在）
     * @param newDto    新数据对象（删除时不存在）
     */
    @Override
    protected void endHandle(OperateConstants.ServiceType operate, int row, SysMenuDto originDto, SysMenuDto newDto) {
        switch (operate) {
            case DELETE -> {
                if (SecurityUserUtils.isAdminTenant()) {
                    SecurityContextHolder.clearTenantIgnore();
                }
            }
            case ADD, EDIT, EDIT_STATUS -> {
                if (SecurityUserUtils.isAdminTenant()) {
                    SecurityContextHolder.rollLastEnterpriseId();
                }
            }
        }
        super.endHandle(operate, row, originDto, newDto);
    }

    /**
     * 批量操作 - 开始处理
     *
     * @param operate 服务层 - 操作类型
     * @param newList 新数据对象集合（删除时不存在）
     * @param idList  Id集合（非删除时不存在）
     */
    @Override
    protected List<SysMenuDto> startBatchHandle(OperateConstants.ServiceType operate, Collection<SysMenuDto> newList, Collection<? extends Serializable> idList) {
        List<SysMenuDto> originList = SecurityContextHolder.setTenantIgnoreFun(() -> super.startBatchHandle(operate, newList, idList));
        if (operate == OperateConstants.ServiceType.BATCH_DELETE) {
            boolean isAdminTenant = SecurityUserUtils.isAdminTenant();
            Long enterpriseId = SecurityUserUtils.getEnterpriseId();
            originList = originList.stream().filter(item -> isAdminTenant || (item.isNotCommon() && ObjectUtil.equals(item.getTenantId(), enterpriseId)))
                    .collect(Collectors.toList());
            if (CollUtil.isEmpty(originList)) {
                throw new ServiceException("无待删除菜单！");
            }

            if (SecurityUserUtils.isAdminTenant()) {
                SecurityContextHolder.setTenantIgnore();
            }
        }
        return originList;
    }

    /**
     * 批量操作 - 结束处理
     *
     * @param operate    服务层 - 操作类型
     * @param rows       操作数据条数
     * @param originList 源数据对象集合（新增时不存在）
     * @param newList    新数据对象集合（删除时不存在）
     */
    @Override
    protected void endBatchHandle(OperateConstants.ServiceType operate, int rows, Collection<SysMenuDto> originList, Collection<SysMenuDto> newList) {
        if (operate == OperateConstants.ServiceType.BATCH_DELETE) {
            if (SecurityUserUtils.isAdminTenant()) {
                SecurityContextHolder.clearTenantIgnore();
            }
        }
        super.endBatchHandle(operate, rows, originList, newList);
    }
}
