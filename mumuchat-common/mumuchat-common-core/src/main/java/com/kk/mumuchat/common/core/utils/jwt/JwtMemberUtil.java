package com.kk.mumuchat.common.core.utils.jwt;

import com.kk.mumuchat.common.core.constant.basic.SecurityConstants;

/**
 * Jwt工具类 | 会员端
 *
 * @author xueyi
 */
public class JwtMemberUtil extends JwtUtil {

    /**
     * 根据令牌获取平台应用Id
     *
     * @param token 令牌
     * @return 平台应用Id
     */
    public static String getApplicationId(String token) {
        Jwt claims = parseToken(token);
        return getApplicationId(claims);
    }

    /**
     * 根据令牌获取平台应用Id
     *
     * @param claims 身份信息
     * @return 平台应用Id
     */
    public static String getApplicationId(Jwt claims) {
        return getValue(claims, SecurityConstants.MemberSecurity.APPLICATION_ID.getCode());
    }

    /**
     * 根据令牌获取应用AppId
     *
     * @param token 令牌
     * @return 应用AppId
     */
    public static String getAppId(String token) {
        Jwt claims = parseToken(token);
        return getAppId(claims);
    }

    /**
     * 根据令牌获取应用AppId
     *
     * @param claims 身份信息
     * @return 应用AppId
     */
    public static String getAppId(Jwt claims) {
        return getValue(claims, SecurityConstants.MemberSecurity.APP_ID.getCode());
    }

    /**
     * 根据令牌获取用户OpenId
     *
     * @param token 令牌
     * @return 用户OpenId
     */
    public static String getOpenId(String token) {
        Jwt claims = parseToken(token);
        return getOpenId(claims);
    }

    /**
     * 根据令牌获取用户OpenId
     *
     * @param claims 身份信息
     * @return 用户OpenId
     */
    public static String getOpenId(Jwt claims) {
        return getValue(claims, SecurityConstants.MemberSecurity.OPEN_ID.getCode());
    }
}
