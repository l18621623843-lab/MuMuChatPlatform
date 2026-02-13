package com.kk.mumuchat.common.security.utils;

import com.kk.mumuchat.common.core.constant.basic.SecurityConstants;
import com.kk.mumuchat.common.core.utils.core.SpringUtil;
import com.kk.mumuchat.common.core.utils.core.StrUtil;
import com.kk.mumuchat.common.security.service.TokenUserService;
import com.kk.mumuchat.system.api.model.DataScope;

/**
 * 管理端 - 权限获取工具类
 *
 * @author mumuchat
 */
public class SecurityUserUtils extends SecurityUtils {

    /** 是否为超管用户 */
    public static boolean isAdminUser() {
        return StrUtil.equals(SecurityConstants.UserType.ADMIN.getCode(), getUserType());
    }

    /** 是否不为超管用户 */
    public static boolean isNotAdminUser() {
        return !isAdminUser();
    }

    /**
     * 获取数据权限信息
     */
    public static DataScope getDataScope() {
        return SpringUtil.getBean(TokenUserService.class).getDataScope();
    }
}
