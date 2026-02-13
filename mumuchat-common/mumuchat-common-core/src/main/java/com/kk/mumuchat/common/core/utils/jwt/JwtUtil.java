package com.kk.mumuchat.common.core.utils.jwt;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.signers.JWTSigner;
import cn.hutool.jwt.signers.JWTSignerUtil;
import com.kk.mumuchat.common.core.constant.basic.SecurityConstants;
import com.kk.mumuchat.common.core.constant.basic.TokenConstants;
import com.kk.mumuchat.common.core.properties.SecretProperties;
import com.kk.mumuchat.common.core.utils.core.ConvertUtil;
import com.kk.mumuchat.common.core.utils.core.StrUtil;

import java.util.Map;
import java.util.Optional;

/**
 * Jwt工具类
 *
 * @author mumuchat
 */
public class JwtUtil {

    /**
     * 验证令牌
     *
     * @param token 令牌
     * @return 验证结果
     */
    public static boolean verify(String token) {
        return JWTUtil.verify(token, SecretProperties.getToken().getBytes());
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    public static Jwt parseToken(String token) {
        return new Jwt(JWT.of(token));
    }

    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    public static String createToken(Map<String, Object> claims) {
        JWTSigner signer = JWTSignerUtil.hs512(SecretProperties.getToken().getBytes());
        return JWT.create()
                .addPayloads(claims)
                .setSigner(signer)
                .sign();
    }

    /**
     * 根据令牌获取企业Id
     *
     * @param token 令牌
     * @return 企业Id
     */

    public static String getEnterpriseId(String token) {
        Jwt claims = parseToken(token);
        return getEnterpriseId(claims);
    }

    /**
     * 根据身份信息获取企业Id
     *
     * @param claims 身份信息
     * @return 企业Id
     */
    public static String getEnterpriseId(Jwt claims) {
        return getValue(claims, SecurityConstants.BaseSecurity.ENTERPRISE_ID.getCode());
    }

    /**
     * 根据令牌获取企业账号
     *
     * @param token 令牌
     * @return 企业账号
     */
    public static String getEnterpriseName(String token) {
        Jwt claims = parseToken(token);
        return getEnterpriseName(claims);
    }

    /**
     * 根据身份信息获取企业账号
     *
     * @param claims 身份信息
     * @return 企业账号
     */
    public static String getEnterpriseName(Jwt claims) {
        return getValue(claims, SecurityConstants.BaseSecurity.ENTERPRISE_NAME.getCode());
    }

    /**
     * 根据令牌获取企业类型
     *
     * @param token 令牌
     * @return 企业类型
     */
    public static String getIsLessor(String token) {
        Jwt claims = parseToken(token);
        return getIsLessor(claims);
    }

    /**
     * 根据身份信息获取企业类型
     *
     * @param claims 身份信息
     * @return 企业类型
     */
    public static String getIsLessor(Jwt claims) {
        return getValue(claims, SecurityConstants.BaseSecurity.IS_LESSOR.getCode());
    }

    /**
     * 根据令牌获取用户Id
     *
     * @param token 令牌
     * @return 用户Id
     */
    public static String getUserId(String token) {
        Jwt claims = parseToken(token);
        return getUserId(claims);
    }

    /**
     * 根据身份信息获取用户Id
     *
     * @param claims 身份信息
     * @return 用户Id
     */
    public static String getUserId(Jwt claims) {
        return getValue(claims, SecurityConstants.BaseSecurity.USER_ID.getCode());
    }

    /**
     * 根据令牌获取用户账号
     *
     * @param token 令牌
     * @return 用户账号
     */
    public static String getUserName(String token) {
        Jwt claims = parseToken(token);
        return getUserName(claims);
    }

    /**
     * 根据身份信息获取用户账号
     *
     * @param claims 身份信息
     * @return 用户账号
     */
    public static String getUserName(Jwt claims) {
        return getValue(claims, SecurityConstants.BaseSecurity.USER_NAME.getCode());
    }

    /**
     * 根据令牌获取用户账号
     *
     * @param token 令牌
     * @return 用户账号
     */
    public static String getNickName(String token) {
        Jwt claims = parseToken(token);
        return getNickName(claims);
    }

    /**
     * 根据身份信息获取用户账号
     *
     * @param claims 身份信息
     * @return 用户账号
     */
    public static String getNickName(Jwt claims) {
        return getValue(claims, SecurityConstants.BaseSecurity.NICK_NAME.getCode());
    }

    /**
     * 根据令牌获取用户类型
     *
     * @param token 令牌
     * @return 用户类型
     */
    public static String getUserType(String token) {
        Jwt claims = parseToken(token);
        return getUserType(claims);
    }

    /**
     * 根据身份信息获取用户类型
     *
     * @param claims 身份信息
     * @return 用户类型
     */
    public static String getUserType(Jwt claims) {
        return getValue(claims, SecurityConstants.BaseSecurity.USER_TYPE.getCode());
    }

    /**
     * 根据令牌获取账户类型
     *
     * @param token 令牌
     * @return 账户类型
     */
    public static String getAccountType(String token) {
        Jwt claims = parseToken(token);
        return getAccountType(claims);
    }

    /**
     * 根据令牌获取账户类型
     *
     * @param claims 身份信息
     * @return 账户类型
     */
    public static String getAccountType(Jwt claims) {
        return getValue(claims, SecurityConstants.BaseSecurity.ACCOUNT_TYPE.getCode());
    }

    /**
     * 根据令牌获取源策略组Id
     *
     * @param token 令牌
     * @return 源策略组Id
     */
    public static String getStrategyId(String token) {
        Jwt claims = parseToken(token);
        return getStrategyId(claims);
    }

    /**
     * 根据令牌获取源策略组Id
     *
     * @param claims 身份信息
     * @return 源策略组Id
     */
    public static String getStrategyId(Jwt claims) {
        return getValue(claims, SecurityConstants.BaseSecurity.STRATEGY_ID.getCode());
    }

    /**
     * 根据令牌获取租户策略源
     *
     * @param token 令牌
     * @return 租户策略源
     */
    public static String getSourceName(String token) {
        Jwt claims = parseToken(token);
        return getSourceName(claims);
    }

    /**
     * 根据令牌获取租户策略源
     *
     * @param claims 身份信息
     * @return 租户策略源
     */
    public static String getSourceName(Jwt claims) {
        return getValue(claims, SecurityConstants.BaseSecurity.SOURCE_NAME.getCode());
    }

    /**
     * 根据令牌获取用户令牌
     *
     * @param token 令牌
     * @return 用户令牌
     */
    public static String getUserKey(String token) {
        Jwt claims = parseToken(token);
        return getUserKey(claims);
    }

    /**
     * 根据令牌获取用户令牌
     *
     * @param claims 身份信息
     * @return 用户令牌
     */
    public static String getUserKey(Jwt claims) {
        String accessToken = getValue(claims, SecurityConstants.BaseSecurity.REFRESH_TOKEN.getCode());
        if (StrUtil.isNotEmpty(accessToken) && StrUtil.startWith(accessToken, TokenConstants.PREFIX)) {
            return StrUtil.replaceFirst(accessToken, TokenConstants.PREFIX, StrUtil.EMPTY);
        }
        return null;
    }

    /**
     * 根据令牌获取平台应用Id
     *
     * @param token 令牌
     * @return 平台应用Id
     */
    public static String getAppId(String token) {
        Jwt claims = parseToken(token);
        return getAppId(claims);
    }

    /**
     * 根据令牌获取平台应用Id
     *
     * @param claims 身份信息
     * @return 平台应用Id
     */
    public static String getAppId(Jwt claims) {
        return getValue(claims, SecurityConstants.MemberSecurity.APP_ID.getCode());
    }

    /**
     * 根据令牌获取访问令牌
     *
     * @param token 令牌
     * @return 访问令牌
     */
    public static String getAccessKey(String token) {
        Jwt claims = parseToken(token);
        return getUserKey(claims);
    }

    /**
     * 根据令牌获取访问令牌
     *
     * @param claims 身份信息
     * @return 访问令牌
     */
    public static String getAccessKey(Jwt claims) {
        return getValue(claims, SecurityConstants.BaseSecurity.ACCESS_TOKEN.getCode());
    }

    /**
     * 根据身份信息获取键值
     *
     * @param claims 身份信息
     * @param key    键
     * @return 值
     */
    public static String getValue(Jwt claims, String key) {
        return Optional.ofNullable(claims)
                .map(Jwt::getJwt)
                .map(item -> item.getPayload(key))
                .map(ConvertUtil::toStr)
                .orElse(StrUtil.EMPTY);
    }
}
