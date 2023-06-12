/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.redick.cloud.account.filter;

import io.redick.cloud.account.configure.properties.GrayProperties;
import io.redick.cloud.account.gray.GrayRequestContextHolder;
import io.redick.cloud.gary.common.GrayConstant;
import lombok.AllArgsConstructor;
import lombok.var;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

/**
 * 灰度发布全局过滤器
 *
 * @author: Redick01
 * @date: 2023/6/6 17:17
 */
@Component
@ConditionalOnProperty(value = "spring.cloud.loadbalancer.gray.enabled", havingValue = "true")
@AllArgsConstructor
public class GrayscalePublishFilter implements GlobalFilter, Ordered {

    private final GrayProperties grayProperties;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        try {
            GrayRequestContextHolder.setGrayTag(false);
            if (grayProperties.getEnabled()) {
                var headers = exchange.getRequest().getHeaders();
                if (headers.containsKey(GrayConstant.HEADER_GRAY_TAG)) {
                    List<String> grayValues = headers.get(GrayConstant.HEADER_GRAY_TAG);
                    if (!Objects.isNull(grayValues) && grayValues.size() > 0) {
                        // 灰度标记为true，直接走灰度
                        String grayValue = grayValues.get(0);
                        // 配置中的值匹配到header中的灰度值，走灰度（可是用户ID，IP，APP版本号等等，只要匹配到就走灰度）
                        if (grayProperties.getMatches().stream().anyMatch(grayValue::equals)) {
                            GrayRequestContextHolder.setGrayTag(true);
                        }
                    }
                }
                var newRequest = exchange.getRequest().mutate()
                        .header(GrayConstant.HEADER_GRAY_TAG, GrayRequestContextHolder.getGrayTag().toString())
                        .build();
                var newExchange = exchange.mutate()
                        .request(newRequest)
                        .build();
                return chain.filter(newExchange);
            }
            return chain.filter(exchange);
        } finally {
            GrayRequestContextHolder.remove();
        }
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
