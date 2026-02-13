package com.kk.mumuchat.common.core.web.model;

import com.kk.mumuchat.common.core.constant.basic.SecurityConstants;
import com.kk.mumuchat.common.core.web.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 企业 基础数据对象
 *
 * @author mumuchat
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysEnterprise extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 源策略组Id */
    protected Long strategyId;

    /** 名称 */
    protected String name;

    /** 系统名称 */
    protected String systemName;

    /** 企业名称 */
    protected String nick;

    /** 企业logo */
    protected String logo;

    /** 超管租户（Y是 N否） */
    protected String isLessor;

    /** 企业账号修改次数 */
    protected Long nameFrequency;

    /** 默认企业（Y是 N否） */
    protected String isDefault;

    /** 企业自定义域名 */
    protected String domainName;

    public boolean isLessor() {
        return SecurityConstants.TenantType.isLessor(getIsLessor());
    }
}