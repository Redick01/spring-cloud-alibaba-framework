package io.redick.cloud.account.auth.impl;

import com.redick.util.LogUtil;
import io.jsonwebtoken.Claims;
import io.redick.cloud.account.auth.Sign;
import io.redick.cloud.account.filter.AuthFilter;
import io.redick.cloud.account.utils.ServletUtils;
import io.redick.cloud.common.constants.Constants;
import io.redick.cloud.common.constants.SecurityConstants;
import io.redick.cloud.common.constants.TokenConstants;
import io.redick.cloud.common.utils.JwtUtils;
import io.redick.cloud.redis.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 *
 * @author Redick01
 */
@Component(value = "jwt")
@Slf4j
public class JwtSign implements Sign {

    @Autowired
    private RedisService redisService;

    @Override
    public Mono<Void> verifySign(ServerWebExchange exchange, GatewayFilterChain chain, AuthFilter.Config config) {
        ServerHttpRequest request = exchange.getRequest();
        val token = getToken(request);
        if (StringUtils.isBlank(token)) {
            return unauthorizedResponse(exchange, "Token is null!");
        }
        Claims claims = JwtUtils.parseToken(token);
        if (null == claims) {
            return unauthorizedResponse(exchange, "The token already expire or verify failed!");
        }
        val userKey = JwtUtils.getUserKey(claims);
        val login = redisService.hasKey(userKey);
        if (!login) {
            return unauthorizedResponse(exchange, "Login status already expire!");
        }
        val userId = JwtUtils.getUserId(claims);
        val userName = JwtUtils.getUserName(claims);
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(userName)) {
            return unauthorizedResponse(exchange, "The token verify failed!");
        }
        // 将用户信息放到请求头
        ServerHttpRequest.Builder mutate = request.mutate();
        addHeader(mutate, SecurityConstants.USER_KEY, userKey);
        addHeader(mutate, SecurityConstants.DETAILS_USER_ID, userId);
        addHeader(mutate, SecurityConstants.DETAILS_USERNAME, userName);
        mutate.headers(httpHeaders -> httpHeaders.remove(SecurityConstants.FROM_SOURCE)).build();
        return chain.filter(exchange.mutate().request(mutate.build()).build());
    }

    /**
     * 从请求头获取token
     * @param request ServerHttpRequest
     * @return token
     */
    private String getToken(ServerHttpRequest request) {
        String token = request.getHeaders().getFirst(TokenConstants.AUTHENTICATION);
        if (StringUtils.isNotBlank(token) && token.startsWith(TokenConstants.PREFIX)) {
            token = token.replaceFirst(TokenConstants.PREFIX, StringUtils.EMPTY);
        }
        return token;
    }

    private Mono<Void> unauthorizedResponse(ServerWebExchange exchange, String message) {
        log.error(LogUtil.marker(exchange.getRequest().getPath()), "Unauthorized response!");
        return ServletUtils.webFluxResponseWriter(exchange.getResponse(), message, HttpStatus.UNAUTHORIZED.value());
    }

    private void addHeader(ServerHttpRequest.Builder mutate, String name, Object value) {
        if (value == null) {
            return;
        }
        String valueStr = value.toString();
        String valueEncode = "";
        try {
            valueEncode = URLEncoder.encode(valueStr, Constants.UTF8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        mutate.header(name, valueEncode);
    }
}
