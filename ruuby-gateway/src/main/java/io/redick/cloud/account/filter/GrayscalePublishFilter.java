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

import io.redick.cloud.account.gray.GrayRequestContextHolder;
import io.redick.cloud.gary.common.GrayConstant;
import lombok.var;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

/**
 * 灰度发布全局拦截器
 *
 * @author: Redick01
 * @date: 2023/6/6 17:17
 */
@Component
public class GrayscalePublishFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        var headers = exchange.getRequest().getHeaders();
        if (headers.containsKey(GrayConstant.HEADER_GRAY_TAG)) {
            List<String> garyValue = headers.get(GrayConstant.HEADER_GRAY_TAG);
            if (!Objects.isNull(garyValue)
                    && garyValue.size() > 0
                    && "true".equals(garyValue.get(0))) {
                GrayRequestContextHolder.setGrayTag(true);
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

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
