package io.redick.cloud.account.filter;

import io.netty.buffer.ByteBufAllocator;
import io.redick.cloud.account.configure.properties.XssProperties;
import io.redick.cloud.account.utils.EscapeUtil;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHeaders;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.*;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
* @author Redick01
*/
@Component
@ConditionalOnProperty(value = "security.xss.enabled", havingValue = "true")
@AllArgsConstructor
public class XssFilter implements GlobalFilter, Ordered {

    private final XssProperties xssProperties;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        if (!xssProperties.getEnabled()) {
            return chain.filter(exchange);
        }
        ServerHttpRequest request = exchange.getRequest();
        // GET DELETE method request not filtered.
        HttpMethod httpMethod = request.getMethod();
        if (null == httpMethod || HttpMethod.GET == httpMethod || HttpMethod.DELETE == httpMethod) {
            return chain.filter(exchange);
        }
        // 非json请求不过滤
        if (!isJsonRequest(request)) {
            return chain.filter(exchange);
        }
        // excludeUrls列表中的URL不过滤
        String url = request.getURI().getPath();
        if (matchExcludeUrls(url, xssProperties.getExcludeUrls())) {
            return chain.filter(exchange);
        }
        // Xss过滤
        ServerHttpRequestDecorator decorator = requestDecorator(exchange);
        return chain.filter(exchange);
    }

    private boolean isJsonRequest(ServerHttpRequest request) {
        String header = request.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE);
        return StringUtils.startsWithIgnoreCase(header, MediaType.APPLICATION_JSON_VALUE);
    }

    private boolean matchExcludeUrls(String url, List<String> excludeUrls) {
        if (StringUtils.isBlank(url)) {
            return false;
        }
        for (String pattern : excludeUrls) {
            AntPathMatcher matcher = new AntPathMatcher();
            return matcher.match(url, pattern);
        }
        return false;
    }

    private ServerHttpRequestDecorator requestDecorator(ServerWebExchange exchange) {

        ServerHttpRequestDecorator decorator = new ServerHttpRequestDecorator(exchange.getRequest()) {
            @Override
            public Flux<DataBuffer> getBody() {
                Flux<DataBuffer> body = super.getBody();
                return body.buffer().map(db -> {
                    DataBufferFactory dataBufferFactory = new DefaultDataBufferFactory();
                    DataBuffer join = dataBufferFactory.join(db);
                    byte[] content = new byte[join.readableByteCount()];
                    join.read(content);
                    DataBufferUtils.release(join);
                    String bodyStr = new String(content, StandardCharsets.UTF_8);
                    // 防xss攻击过滤
                    bodyStr = EscapeUtil.clean(bodyStr);
                    // 转成字节
                    byte[] bytes = bodyStr.getBytes();
                    NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory(ByteBufAllocator.DEFAULT);
                    DataBuffer buffer = nettyDataBufferFactory.allocateBuffer(bytes.length);
                    buffer.write(bytes);
                    return buffer;
                });
            }

            @Override
            public org.springframework.http.HttpHeaders getHeaders()
            {
                org.springframework.http.HttpHeaders httpHeaders = new org.springframework.http.HttpHeaders();
                httpHeaders.putAll(super.getHeaders());
                // 由于修改了请求体的body，导致content-length长度不确定，因此需要删除原先的content-length
                httpHeaders.remove(org.springframework.http.HttpHeaders.CONTENT_LENGTH);
                httpHeaders.set(org.springframework.http.HttpHeaders.TRANSFER_ENCODING, "chunked");
                return httpHeaders;
            }
        };
        return decorator;
    }

    @Override
    public int getOrder() {
        return -100;
    }
}
