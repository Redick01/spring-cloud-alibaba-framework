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

package io.redick.cloud.gary.configure;

import io.redick.cloud.gary.loadbalancer.GrayscaleLoadBalancer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @author: Redick01
 * @date: 2023/6/8 17:04
 */
@Configuration
public class LoadBalancerGrayAutoConfiguration {

    /**
     * 自定义LoadBalancer
     * @param environment Environment
     * @param loadBalancerClientFactory LoadBalancerClientFactory
     * @return LoadBalancer
     */
    @Bean
    @ConditionalOnProperty(value = "spring.cloud.loadbalancer.gray.enabled", havingValue = "true", matchIfMissing = false)
    @ConditionalOnBean(LoadBalancerClientFactory.class)
    public ReactorLoadBalancer<ServiceInstance> grayReactorLoadBalancer(Environment environment,
                                                                        LoadBalancerClientFactory loadBalancerClientFactory) {
        String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
        return new GrayscaleLoadBalancer(name, loadBalancerClientFactory.getLazyProvider(name, ServiceInstanceListSupplier.class));
    }
}
