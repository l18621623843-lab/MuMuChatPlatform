package com.kk.mumuchat.common.security.auth.pool;

import com.kk.mumuchat.common.security.auth.pool.tenant.TeSourcePool;
import com.kk.mumuchat.common.security.auth.pool.tenant.TeTenantPool;

/**
 * 租户服务 权限标识常量
 *
 * @author xueyi
 */
public interface TenantPool extends TeTenantPool, TeSourcePool {
}
