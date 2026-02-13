package com.kk.mumuchat.system.organize.manager.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.kk.mumuchat.common.core.constant.basic.BaseConstants;
import com.kk.mumuchat.common.web.entity.manager.impl.BaseManagerImpl;
import com.kk.mumuchat.system.api.organize.domain.dto.SysEnterpriseDto;
import com.kk.mumuchat.system.api.organize.domain.po.SysEnterprisePo;
import com.kk.mumuchat.system.api.organize.domain.query.SysEnterpriseQuery;
import com.kk.mumuchat.system.organize.domain.model.SysEnterpriseConverter;
import com.kk.mumuchat.system.organize.manager.ISysEnterpriseManager;
import com.kk.mumuchat.system.organize.mapper.SysEnterpriseMapper;
import org.springframework.stereotype.Component;

/**
 * 系统服务|组织模块|企业管理 数据封装层处理
 *
 * @author mumuchat
 */
@Component
public class SysEnterpriseManagerImpl extends BaseManagerImpl<SysEnterpriseQuery, SysEnterpriseDto, SysEnterprisePo, SysEnterpriseMapper, SysEnterpriseConverter> implements ISysEnterpriseManager {

    /**
     * 根据名称查询状态正常企业对象
     *
     * @param name 名称
     * @return 企业对象
     */
    @Override
    public SysEnterpriseDto selectByName(String name) {
        SysEnterprisePo enterprise = baseMapper.selectOne(
                Wrappers.<SysEnterprisePo>query().lambda()
                        .eq(SysEnterprisePo::getName, name)
                        .eq(SysEnterprisePo::getStatus, BaseConstants.Status.NORMAL.getCode()));
        return mapperDto(enterprise);
    }
}
