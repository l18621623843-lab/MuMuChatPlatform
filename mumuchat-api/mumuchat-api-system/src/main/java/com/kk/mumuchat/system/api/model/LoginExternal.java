package com.kk.mumuchat.system.api.model;

import com.kk.mumuchat.common.core.constant.basic.SecurityConstants;
import com.kk.mumuchat.common.core.web.model.BaseLoginUser;
import com.kk.mumuchat.system.api.organize.domain.dto.SysUserDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 外系统端 - 用户信息
 *
 * @author xueyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LoginExternal extends BaseLoginUser<SysUserDto> {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 认证签名 */
    private String signature;

    /** 外系统令牌 */
    private String secret;

    /** 账户类型 */
    private SecurityConstants.AccountType accountType = SecurityConstants.AccountType.EXTERNAL;

    /** 初始化用户信息 */
    public void initUser(SysUserDto user) {
        setUser(user);
        setUserId(user.getId());
        setUserName(user.getUserName());
        setUserType(user.getUserType());
        setPassword(user.getPassword());
    }
}
