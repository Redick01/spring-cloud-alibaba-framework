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

package io.redick.cloud.gary.loadbalancer;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import io.redick.cloud.gary.common.GrayConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.*;
import org.springframework.cloud.loadbalancer.core.NoopServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.SelectedInstanceCallback;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author: Redick01
 * @date: 2023/6/7 13:14
 */
@Slf4j
public class GrayscaleLoadBalancer implements ReactorServiceInstanceLoadBalancer {

    final AtomicInteger position;

    final String serviceId;

    private final ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider;

    public GrayscaleLoadBalancer(String serviceId, ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider) {
        this(new Random().nextInt(1000), serviceId, serviceInstanceListSupplierProvider);
    }

    public GrayscaleLoadBalancer(int seedPosition, String serviceId, ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider) {
        this.position = new AtomicInteger(seedPosition);
        this.serviceId = serviceId;
        this.serviceInstanceListSupplierProvider = serviceInstanceListSupplierProvider;
    }

    @Override
    public Mono<Response<ServiceInstance>> choose(Request request) {
        ServiceInstanceListSupplier supplier = serviceInstanceListSupplierProvider
                .getIfAvailable(NoopServiceInstanceListSupplier::new);
        return supplier.get(request).next()
                .map(serviceInstances -> processInstanceResponse(supplier, serviceInstances, request));
    }

    private Response<ServiceInstance> processInstanceResponse(ServiceInstanceListSupplier supplier,
                                                              List<ServiceInstance> serviceInstances,
                                                              Request request) {
        Response<ServiceInstance> serviceInstanceResponse = getInstanceResponse(serviceInstances, request);
        if (supplier instanceof SelectedInstanceCallback && serviceInstanceResponse.hasServer()) {
            ((SelectedInstanceCallback) supplier).selectedServiceInstance(serviceInstanceResponse.getServer());
        }
        return serviceInstanceResponse;
    }

    private Response<ServiceInstance> getInstanceResponse(List<ServiceInstance> instances, Request request) {
        if (instances.isEmpty()) {
            if (log.isWarnEnabled()) {
                log.warn("No servers available for service: " + serviceId);
            }
            return new EmptyResponse();
        }
        // 获取ServiceInstance列表

        instances = getInstances(instances, request);
        // Do not move position when there is only 1 instance, especially some suppliers
        // have already filtered instances
        if (instances.size() == 1) {
            return new DefaultResponse(instances.get(0));
        }

        // Ignore the sign bit, this allows pos to loop sequentially from 0 to
        // Integer.MAX_VALUE
        int pos = this.position.incrementAndGet() & Integer.MAX_VALUE;

        ServiceInstance instance = instances.get(pos % instances.size());

        return new DefaultResponse(instance);
    }

    private List<ServiceInstance> getInstances(List<ServiceInstance> instances, Request request) {
        DefaultRequest<RequestDataContext> defaultRequest = Convert
                .convert(new TypeReference<DefaultRequest<RequestDataContext>>() {
                }, request);
        RequestDataContext dataContext = defaultRequest.getContext();
        RequestData requestData = dataContext.getClientRequest();
        HttpHeaders headers = requestData.getHeaders();
        // 获取灰度标记
        String gray = CollectionUtil.get(headers.get(GrayConstant.HEADER_GRAY_TAG), 0);
        // 灰度标记不为空并且标记为true, 筛选ServiceInstance
        if (StringUtils.isNotBlank(gray) && StringUtils.equals(GrayConstant.HEADER_GRAY_TAG_VALUE, gray)) {
            return instances.stream()
                    .filter(instance -> StringUtils.isNotBlank(instance.getMetadata().get(GrayConstant.HEADER_GRAY_TAG))
                            && gray.equals(instance.getMetadata().get(GrayConstant.HEADER_GRAY_TAG)))
                    .collect(Collectors.toList());
        } else {
            return instances;
        }
    }
}
