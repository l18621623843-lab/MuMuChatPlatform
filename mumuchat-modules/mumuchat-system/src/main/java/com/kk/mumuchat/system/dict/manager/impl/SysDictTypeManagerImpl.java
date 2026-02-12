package com.kk.mumuchat.system.dict.manager.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.kk.mumuchat.common.core.constant.basic.SqlConstants;
import com.kk.mumuchat.common.core.constant.basic.TenantConstants;
import com.kk.mumuchat.common.web.entity.manager.impl.BaseManagerImpl;
import com.kk.mumuchat.system.api.dict.domain.dto.SysDictTypeDto;
import com.kk.mumuchat.system.api.dict.domain.po.SysDictTypePo;
import com.kk.mumuchat.system.api.dict.domain.query.SysDictTypeQuery;
import com.kk.mumuchat.system.dict.domain.model.SysDictTypeConverter;
import com.kk.mumuchat.system.dict.manager.ISysDictTypeManager;
import com.kk.mumuchat.system.dict.mapper.SysDictTypeMapper;
import org.springframework.stereotype.Component;

/**
 * 系统服务|字典模块|字典类型管理 数据封装层处理
 *
 * @author xueyi
 */
@Component
public class SysDictTypeManagerImpl extends BaseManagerImpl<SysDictTypeQuery, SysDictTypeDto, SysDictTypePo, SysDictTypeMapper, SysDictTypeConverter> implements ISysDictTypeManager {

    /**
     * 校验字典编码是否唯一
     *
     * @param Id   字典类型Id
     * @param code 字典类型编码
     * @return 字典类型对象
     */
    @Override
    public SysDictTypeDto checkDictCodeUnique(Long Id, String code) {
        SysDictTypePo dictType = baseMapper.selectOne(
                Wrappers.<SysDictTypePo>lambdaQuery()
                        .ne(SysDictTypePo::getId, Id)
                        .eq(SysDictTypePo::getCode, code)
                        .eq(SysDictTypePo::getTenantId, TenantConstants.COMMON_TENANT_ID)
                        .last(SqlConstants.LIMIT_ONE));
        return baseConverter.mapperDto(dictType);
    }
}
