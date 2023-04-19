package io.redick.cloud.account.auth.impl;

import io.redick.cloud.account.auth.Sign;
import io.redick.cloud.account.filter.AuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Redick01
 */
@Component
public class SignHandler {

    @Autowired
    private Map<String, Sign> signMap = new ConcurrentHashMap<>();

    public Mono<Void> sign(ServerWebExchange exchange, GatewayFilterChain chain, AuthFilter.Config config) {
        Sign sign = signMap.get(config.getType());
        return sign.verifySign(exchange, chain, config);
    }
}
