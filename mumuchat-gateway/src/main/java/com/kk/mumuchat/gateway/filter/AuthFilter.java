package com.kk.mumuchat.gateway.filter;

import com.kk.mumuchat.common.core.constant.basic.SecurityConstants;
import com.kk.mumuchat.common.core.constant.basic.TokenConstants;
import com.kk.mumuchat.common.core.utils.core.ObjectUtil;
import com.kk.mumuchat.common.core.utils.core.StrUtil;
import com.kk.mumuchat.common.core.utils.jwt.Jwt;
import com.kk.mumuchat.common.core.utils.jwt.JwtMemberUtil;
import com.kk.mumuchat.common.core.utils.jwt.JwtUtil;
import com.kk.mumuchat.common.core.utils.servlet.ServletUtil;
import com.kk.mumuchat.common.redis.service.RedisService;
import com.kk.mumuchat.gateway.config.properties.IgnoreWhiteProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * 网关鉴权
 *
 * @author mumuchat
 */
@Slf4j
public class AuthFilter implements WebFilter {

    private final RedisService redisService;

    private final IgnoreWhiteProperties ignoreWhite;

    public AuthFilter(RedisService redisService, IgnoreWhiteProperties ignoreWhite) {
        this.redisService = redisService;
        this.ignoreWhite = ignoreWhite;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers = new HttpHeaders();
        headers.addAll(request.getHeaders());
        String url = request.getURI().getPath();

        boolean isWhites = StrUtil.matches(url, ignoreWhite.getWhites());

        String token = ServletUtil.getToken(request);
        if (StrUtil.isEmpty(token)) {
            return unauthorizedResponse(exchange, chain, isWhites, "令牌不能为空");
        }
        Jwt claims;
        try {
            if (JwtUtil.verify(token)) {
                claims = JwtUtil.parseToken(token);
            } else {
                return unauthorizedResponse(exchange, chain, isWhites, "令牌已过期或验证不正确");
            }
        } catch (Exception e) {
            return unauthorizedResponse(exchange, chain, isWhites, "令牌已过期或验证不正确");
        }
        if (ObjectUtil.isNull(claims)) {
            return unauthorizedResponse(exchange, chain, isWhites, "令牌已过期或验证不正确");
        }

        String accessTokenPrefix = JwtUtil.getAccessKey(claims);
        if (StrUtil.isBlank(accessTokenPrefix) || !StrUtil.startWith(accessTokenPrefix, TokenConstants.PREFIX)) {
            return unauthorizedResponse(exchange, chain, isWhites, "令牌已过期或验证不正确");
        }
        String accessToken = StrUtil.replaceFirst(accessTokenPrefix, TokenConstants.PREFIX, StrUtil.EMPTY);
        String userKey = JwtUtil.getUserKey(claims);
        if (StrUtil.isBlank(userKey)) {
            return unauthorizedResponse(exchange, chain, isWhites, "令牌已过期或验证不正确");
        }
        SecurityConstants.AccountType accountType = SecurityConstants.AccountType.getByCodeElseNull(JwtUtil.getAccountType(claims));
        if (ObjectUtil.isNull(accountType)) {
            return unauthorizedResponse(exchange, chain, isWhites, "令牌已过期或验证不正确");
        }

        boolean hasLogin = redisService.hasKey(accessToken) && redisService.hasKey(userKey);
        if (!hasLogin) {
            return unauthorizedResponse(exchange, chain, isWhites, "登录状态已过期");
        }

        // JWT解析信息
        String baseEnterpriseId = JwtUtil.getEnterpriseId(claims);
        String baseEnterpriseName = JwtUtil.getEnterpriseName(claims);
        String baseIsLessor = JwtUtil.getIsLessor(claims);
        String baseStrategyId = JwtUtil.getStrategyId(claims);
        String baseSourceName = JwtUtil.getSourceName(claims);
        String baseUserId = JwtUtil.getUserId(claims);
        String baseUserName = JwtUtil.getUserName(claims);
        String baseNickName = JwtUtil.getNickName(claims);
        String baseUserType = JwtUtil.getUserType(claims);

        if (StrUtil.hasBlank(baseEnterpriseId, baseEnterpriseName, baseIsLessor, baseSourceName)) {
            return unauthorizedResponse(exchange, chain, isWhites, "令牌验证失败");
        }

        switch (accountType) {
            case ADMIN, MERCHANT, EXTERNAL -> {
                if (StrUtil.hasBlank(baseUserId, baseUserName, baseUserType)) {
                    return unauthorizedResponse(exchange, chain, isWhites, "令牌验证失败");
                }
            }
            case MEMBER -> {
                String applicationId = JwtMemberUtil.getApplicationId(claims);
                String appId = JwtMemberUtil.getAppId(claims);

                if (StrUtil.hasBlank(appId)) {
                    return unauthorizedResponse(exchange, chain, isWhites, "令牌验证失败");
                }

                ServletUtil.addHeader(headers, SecurityConstants.MemberSecurity.APPLICATION_ID.getBaseCode(), applicationId);
                ServletUtil.addHeader(headers, SecurityConstants.MemberSecurity.APP_ID.getBaseCode(), appId);
            }
            default -> {
                return unauthorizedResponse(exchange, chain, isWhites, "令牌验证失败");
            }
        }

        // 设置Token解析信息到请求
        ServletUtil.addHeader(headers, SecurityConstants.BaseSecurity.ENTERPRISE_ID.getBaseCode(), baseEnterpriseId);
        ServletUtil.addHeader(headers, SecurityConstants.BaseSecurity.ENTERPRISE_NAME.getBaseCode(), baseEnterpriseName);
        ServletUtil.addHeader(headers, SecurityConstants.BaseSecurity.ACCOUNT_TYPE.getBaseCode(), accountType.getCode());
        ServletUtil.addHeader(headers, SecurityConstants.BaseSecurity.IS_LESSOR.getBaseCode(), baseIsLessor);
        ServletUtil.addHeader(headers, SecurityConstants.BaseSecurity.STRATEGY_ID.getBaseCode(), baseStrategyId);
        ServletUtil.addHeader(headers, SecurityConstants.BaseSecurity.SOURCE_NAME.getBaseCode(), baseSourceName);

        ServletUtil.addHeader(headers, SecurityConstants.BaseSecurity.USER_ID.getBaseCode(), baseUserId);
        ServletUtil.addHeader(headers, SecurityConstants.BaseSecurity.USER_NAME.getBaseCode(), baseUserName);
        ServletUtil.addHeader(headers, SecurityConstants.BaseSecurity.NICK_NAME.getBaseCode(), baseNickName);
        ServletUtil.addHeader(headers, SecurityConstants.BaseSecurity.USER_TYPE.getBaseCode(), baseUserType);

        // 令牌信息
        ServletUtil.addHeader(headers, SecurityConstants.BaseSecurity.ACCESS_TOKEN.getCode(), accessTokenPrefix);
        ServletUtil.addHeader(headers, SecurityConstants.BaseSecurity.REFRESH_TOKEN.getCode(), userKey);
        ServletUtil.addHeader(headers, SecurityConstants.BaseSecurity.USER_KEY.getCode(), userKey);

        // 内部请求来源参数清除
        ServletUtil.removeHeader(headers, SecurityConstants.BaseSecurity.FROM_SOURCE.getCode());
        if (!isWhites) {
            ServletUtil.removeHeader(headers, TokenConstants.SUPPLY_AUTHORIZATION);
        }
        ServerHttpRequest modifiedRequest = new ServerHttpRequestDecorator(request) {
            @Override
            public HttpHeaders getHeaders() {
                return headers;
            }
        };
        return chain.filter(exchange.mutate().request(modifiedRequest).build());
    }

    private Mono<Void> unauthorizedResponse(ServerWebExchange exchange, WebFilterChain chain, boolean isWhites, String msg) {
        return isWhites ? chain.filter(exchange) : ServletUtil.unauthorizedResponse(exchange, msg);
    }
}