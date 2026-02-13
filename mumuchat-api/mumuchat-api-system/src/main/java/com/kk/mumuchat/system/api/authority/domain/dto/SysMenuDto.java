package com.kk.mumuchat.system.api.authority.domain.dto;

import com.kk.mumuchat.common.core.utils.core.StrUtil;
import com.kk.mumuchat.system.api.authority.constant.AuthorityConstants;
import com.kk.mumuchat.system.api.authority.domain.po.SysMenuPo;
import com.kk.mumuchat.system.api.organize.domain.dto.SysEnterpriseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 系统服务|权限模块|菜单 数据传输对象
 *
 * @author mumuchat
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysMenuDto extends SysMenuPo {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 菜单全路径 */
    private String fullPath;

    /** 详情页激活的菜单 */
    private String currentActiveMenu;

    /** 模块信息 */
    private SysModuleDto module;

    /** 企业信息 */
    private SysEnterpriseDto enterpriseInfo;

    /**
     * 校验菜单类型是否为目录
     */
    public boolean isDir() {
        return StrUtil.equals(AuthorityConstants.MenuType.DIR.getCode(), getMenuType());
    }

    /**
     * 校验菜单类型是否为菜单
     */
    public boolean isMenu() {
        return StrUtil.equals(AuthorityConstants.MenuType.MENU.getCode(), getMenuType());
    }

    /**
     * 校验菜单类型是否为详情
     */
    public boolean isDetails() {
        return StrUtil.equals(AuthorityConstants.MenuType.DETAILS.getCode(), getMenuType());
    }

    /**
     * 校验页面类型是否为内链
     */
    public boolean isEmbedded() {
        return StrUtil.equals(AuthorityConstants.FrameType.EMBEDDED.getCode(), getFrameType());
    }

    /**
     * 校验页面类型是否为外链
     */
    public boolean isExternalLinks() {
        return StrUtil.equals(AuthorityConstants.FrameType.EXTERNAL_LINKS.getCode(), getFrameType());
    }

    /**
     * 移除详情菜单动态后缀
     */
    public String getDetailsSuffix() {
        return isDetails() && getPath().contains(StrUtil.COLON)
                ? StrUtil.sub(getPath(), 0, getPath().indexOf(StrUtil.COLON))
                : getPath();
    }
}
