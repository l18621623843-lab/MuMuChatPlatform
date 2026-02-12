package com.kk.mumuchat.common.security.auth;

import com.kk.mumuchat.common.security.auth.pool.GenPool;
import com.kk.mumuchat.common.security.auth.pool.JobPool;
import com.kk.mumuchat.common.security.auth.pool.SystemPool;
import com.kk.mumuchat.common.security.auth.pool.TenantPool;

/**
 * 权限标识常量
 *
 * @author xueyi
 */
public class Auth implements SystemPool, JobPool, GenPool, TenantPool {
}
