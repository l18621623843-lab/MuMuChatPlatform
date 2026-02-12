package com.kk.mumuchat.system.dict.manager.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.kk.mumuchat.common.core.constant.basic.SqlConstants;
import com.kk.mumuchat.common.core.constant.basic.TenantConstants;
import com.kk.mumuchat.common.web.entity.manager.impl.BaseManagerImpl;
import com.kk.mumuchat.system.api.dict.domain.dto.SysImExDto;
import com.kk.mumuchat.system.api.dict.domain.po.SysImExPo;
import com.kk.mumuchat.system.api.dict.domain.query.SysImExQuery;
import com.kk.mumuchat.system.dict.domain.model.SysImExConverter;
import com.kk.mumuchat.system.dict.manager.ISysImExManager;
import com.kk.mumuchat.system.dict.mapper.SysImExMapper;
import org.springframework.stereotype.Component;

/**
 * 导入导出配置管理 数据封装层处理
 *
 * @author xueyi
 */
@Component
public class SysImExManagerImpl extends BaseManagerImpl<SysImExQuery, SysImExDto, SysImExPo, SysImExMapper, SysImExConverter> implements ISysImExManager {


    /**
     * 根据配置编码查询配置对象
     *
     * @param code 配置编码
     * @return 配置对象
     */
    @Override
    public SysImExDto selectByCode(String code) {
        SysImExPo config = baseMapper.selectOne(
                Wrappers.<SysImExPo>lambdaQuery()
                        .eq(SysImExPo::getCode, code)
                        .last(SqlConstants.LIMIT_ONE));
        return baseConverter.mapperDto(config);
    }

    /**
     * 校验配置编码是否唯一
     *
     * @param id   配置Id
     * @param code 配置编码
     * @return 配置对象
     */
    @Override
    public SysImExDto checkCodeUnique(Long id, String code) {
        SysImExPo config = baseMapper.selectOne(
                Wrappers.<SysImExPo>lambdaQuery()
                        .ne(SysImExPo::getId, id)
                        .eq(SysImExPo::getCode, code)
                        .eq(SysImExPo::getTenantId, TenantConstants.COMMON_TENANT_ID)
                        .last(SqlConstants.LIMIT_ONE));
        return baseConverter.mapperDto(config);
    }
}