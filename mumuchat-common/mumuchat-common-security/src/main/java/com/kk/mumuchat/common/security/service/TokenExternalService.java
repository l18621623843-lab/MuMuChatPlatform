package com.kk.mumuchat.common.security.service;

import com.kk.mumuchat.common.cache.constant.CacheConstants;
import com.kk.mumuchat.common.core.constant.basic.SecurityConstants;
import com.kk.mumuchat.common.core.utils.core.ObjectUtil;
import com.kk.mumuchat.common.core.utils.core.StrUtil;
import com.kk.mumuchat.common.redis.service.RedisService;
import com.kk.mumuchat.system.api.model.LoginExternal;
import com.kk.mumuchat.system.api.organize.domain.dto.SysUserDto;
import jakarta.annotation.Resource;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * token控制器 | 外系统账户
 *
 * @author mumuchat
 */
@Getter
@Component
public class TokenExternalService implements ITokenService<SysUserDto, LoginExternal> {

    @Resource
    private RedisService redisService;

    /**
     * 判断账户类型
     *
     * @param accountType 账户类型
     * @return 结果
     */
    @Override
    public boolean support(String accountType) {
        return StrUtil.equals(SecurityConstants.AccountType.EXTERNAL.getCode(), accountType);
    }

    /**
     * 构建令牌缓存路径
     *
     * @param type         密钥类型
     * @param enterpriseId 企业Id
     * @param userId       用户名Id
     * @param tokenValue   token值
     * @return 令牌缓存路径
     */
    @Override
    public String getTokenAddress(String type, Long enterpriseId, Long userId, String tokenValue) {
        if (ObjectUtil.isNull(enterpriseId) || StrUtil.isBlank(tokenValue)) {
            throw new NullPointerException("enterpriseId or tokenValue has empty");
        }
        return StrUtil.format("{}:{}:{}:{}:{}:{}", CacheConstants.AUTHORIZATION, CacheConstants.LoginTokenType.EXTERNAL.getCode(), enterpriseId, userId, type, tokenValue);
    }

    /**
     * 创建令牌
     *
     * @param loginUser 登录信息
     * @return JWT令牌
     */
    @Override
    public Map<String, Object> createToken(LoginExternal loginUser) {
        Map<String, Object> rspMap = ITokenService.super.createToken(loginUser);
        rspMap.put(SecurityConstants.ExternalSecurity.SIGNATURE.getCode(), loginUser.getSignature());
        return rspMap;
    }
}
