package com.kk.mumuchat.common.security.auth.pool;

import com.kk.mumuchat.common.security.auth.pool.system.SysAuthorityPool;
import com.kk.mumuchat.common.security.auth.pool.system.SysDictPool;
import com.kk.mumuchat.common.security.auth.pool.system.SysExternalPool;
import com.kk.mumuchat.common.security.auth.pool.system.SysFilePool;
import com.kk.mumuchat.common.security.auth.pool.system.SysMonitorPool;
import com.kk.mumuchat.common.security.auth.pool.system.SysNoticePool;
import com.kk.mumuchat.common.security.auth.pool.system.SysOrganizePool;

/**
 * 系统服务 权限标识常量
 *
 * @author mumuchat
 */
public interface SystemPool extends SysAuthorityPool, SysDictPool, SysFilePool, SysMonitorPool, SysNoticePool, SysOrganizePool, SysExternalPool {
}
