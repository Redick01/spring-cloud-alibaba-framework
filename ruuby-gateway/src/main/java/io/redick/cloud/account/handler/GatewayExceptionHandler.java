package io.redick.cloud.account.handler;

import com.redick.util.LogUtil;
import io.redick.cloud.account.utils.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author Redick01
 */
@Slf4j
public class GatewayExceptionHandler implements ErrorWebExceptionHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();
        if (exchange.getResponse().isCommitted()) {
            return Mono.error(ex);
        }
        String message;
        if (ex instanceof NotFoundException) {
            message = "Service not found!";
        } else if (ex instanceof ResponseStatusException) {
            ResponseStatusException responseStatusException = (ResponseStatusException) ex;
            message = responseStatusException.getMessage();
        } else {
            message = "Service internal error!";
        }
        log.error(LogUtil.exceptionMarker(), message + "path:" + exchange.getRequest().getPath(), ex);
        return ServletUtils.webFluxResponseWriter(response, message);
    }
}
