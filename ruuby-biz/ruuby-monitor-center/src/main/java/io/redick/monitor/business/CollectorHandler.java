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

package io.redick.monitor.business;

import io.redick.monitor.endpoint.Response;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * @author: Redick01
 * @date: 2024/3/13 16:30
 */
@Component
@AllArgsConstructor
public class CollectorHandler {

    private static final String ERROR = "DOWN";

    private final Map<String, BusinessCollector> businessCollectorMap;

    private final LoadBalancerClient loadBalancerClient;

    private final RestTemplate restTemplate;

    public String doCollect(String key) {
        String r = "UP";
        if (businessCollectorMap.containsKey(key)) {
            BusinessCollector collector = businessCollectorMap.get(key);
            List<String> services = collector.serviceList();
            for (String s : services) {
                ServiceInstance serviceInstance = loadBalancerClient.choose(s);
                String result = ping(serviceInstance);
                if (ERROR.equals(result)) {
                    r = ERROR;
                    break;
                }
            }
        } else {
            r = ERROR;
        }
        return r;
    }

    private String ping(ServiceInstance serviceInstance) {
        String url = String.format("http://%s:%s/actuator/health", serviceInstance.getHost(),serviceInstance.getPort());
        Response response = restTemplate.getForObject(url, Response.class);
        return response != null ? response.getStatus() : "DOWN";
    }
}
