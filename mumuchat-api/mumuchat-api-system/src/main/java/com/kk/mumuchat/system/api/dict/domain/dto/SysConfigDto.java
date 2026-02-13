package com.kk.mumuchat.system.api.dict.domain.dto;

import com.kk.mumuchat.system.api.dict.domain.po.SysConfigPo;
import com.kk.mumuchat.system.api.organize.domain.dto.SysEnterpriseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 系统服务|字典模块|参数 数据传输对象
 *
 * @author mumuchat
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysConfigDto extends SysConfigPo {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 企业信息 */
    private SysEnterpriseDto enterpriseInfo;

}
