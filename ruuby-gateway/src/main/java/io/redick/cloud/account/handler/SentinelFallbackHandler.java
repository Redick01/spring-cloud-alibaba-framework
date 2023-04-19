package io.redick.cloud.account.handler;

import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import com.redick.util.LogUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author Redick01
 */
@Slf4j
public class SentinelFallbackHandler implements BlockRequestHandler {

    @Override
    public Mono<ServerResponse> handleRequest(ServerWebExchange serverWebExchange,
            Throwable throwable) {
        log.info(LogUtil.marker(), "SCG Sentinel blocked!");
        return ServerResponse
                .status(444)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue("SCG Sentinel blocked!"));
    }
}
