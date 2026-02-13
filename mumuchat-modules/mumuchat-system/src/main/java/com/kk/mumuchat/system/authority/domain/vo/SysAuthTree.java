package com.kk.mumuchat.system.authority.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kk.mumuchat.common.core.constant.basic.BaseConstants;
import com.kk.mumuchat.common.core.utils.core.ObjectUtil;
import com.kk.mumuchat.common.core.web.entity.base.BasisEntity;
import com.kk.mumuchat.system.api.authority.constant.AuthorityConstants;
import com.kk.mumuchat.system.api.authority.domain.dto.SysMenuDto;
import com.kk.mumuchat.system.api.authority.domain.dto.SysModuleDto;
import com.kk.mumuchat.system.api.authority.domain.po.meta.SysMenuMetaPo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;


/**
 * 权限对象 通用结构
 *
 * @author mumuchat
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SysAuthTree extends BasisEntity {

    /** Id */
    private Long id;

    /** 父级Id */
    private Long parentId;

    /** 名称 */
    private String label;

    /** 状态 */
    private String status;

    /** 类型（0|模块 1|菜单 2|版本号） */
    private String type;

    /** 图标 */
    private String icon;

    /** 菜单类型 */
    private String menuType;

    /** 子权限 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<SysAuthTree> children;

    /**
     * 模块转换
     */
    public SysAuthTree(SysModuleDto module) {
        this.id = module.getId();
        this.parentId = BaseConstants.TOP_ID;
        this.label = module.getName();
        this.status = module.getStatus();
        this.type = AuthorityConstants.AuthorityType.MODULE.getCode();
    }

    /**
     * 菜单转换
     */
    public SysAuthTree(SysMenuDto menu) {
        this.id = menu.getId();
        this.parentId = ObjectUtil.equals(menu.getParentId(), AuthorityConstants.MENU_TOP_NODE) ? menu.getModuleId() : menu.getParentId();
        this.label = menu.getTitle();
        this.status = menu.getStatus();
        this.icon = Optional.ofNullable(menu.getMetaInfo()).map(SysMenuMetaPo::getIcon).orElse(null);
        this.menuType = menu.getMenuType();
        this.type = AuthorityConstants.AuthorityType.MENU.getCode();
    }
}
