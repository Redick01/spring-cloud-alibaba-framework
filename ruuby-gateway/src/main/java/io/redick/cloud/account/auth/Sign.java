package io.redick.cloud.account.auth;

import io.redick.cloud.account.filter.AuthFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author Redick01
 */
public interface Sign {

    Mono<Void> verifySign(ServerWebExchange exchange, GatewayFilterChain chain, AuthFilter.Config config);
}
