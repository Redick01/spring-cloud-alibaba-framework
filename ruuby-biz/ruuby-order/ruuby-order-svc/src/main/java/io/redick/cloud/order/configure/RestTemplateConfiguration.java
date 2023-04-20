package io.redick.cloud.order.configure;

import com.alibaba.cloud.sentinel.annotation.SentinelRestTemplate;
import com.redick.support.resttemplate.TraceIdRestTemplateInterceptor;
import io.redick.cloud.order.util.SentinelHandleUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Redick01
 */
@Configuration
public class RestTemplateConfiguration {

    @Bean
    @SentinelRestTemplate(blockHandler = "blockHandle", blockHandlerClass = SentinelHandleUtil.class,
            fallback = "fallback", fallbackClass = SentinelHandleUtil.class)
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        // 链路追踪
        restTemplate.setInterceptors(Stream.of(new TraceIdRestTemplateInterceptor()).collect(Collectors.toList()));
        return restTemplate;
    }
}
