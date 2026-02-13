package com.kk.mumuchat.system.utils.V5.model;

import com.kk.mumuchat.system.api.authority.domain.po.meta.SysMenuMetaPo;
import com.kk.mumuchat.system.utils.V5.route.MetaVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * 系统服务|路由 对象映射器
 *
 * @author mumuchat
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SysRouteConverter {

    /** 映射Meta */
    MetaVo mapperMeta(SysMenuMetaPo po);

}
