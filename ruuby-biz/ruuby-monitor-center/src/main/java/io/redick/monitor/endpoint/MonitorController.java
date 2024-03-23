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

package io.redick.monitor.endpoint;

import com.google.common.collect.Lists;
import com.redick.annotation.LogMarker;
import io.redick.monitor.business.CollectorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: Redick01
 * @date: 2024/3/15 15:31
 */
@RestController
@RequestMapping("/monitor")
public class MonitorController {

    @Autowired
    private CollectorHandler collectorHandler;

    @GetMapping(path = "/echo/{business}")
    @LogMarker(businessDescription = "健康监测")
    public List<Response> monitor(@PathVariable String business) {
        List<Response> responses = Lists.newArrayList();
        // 解析参数
        String[] strings = business.split(",");
        for (String string : strings) {
            String status = collectorHandler.doCollect(string);
            Response response = new Response(string, status);
            responses.add(response);
        }
        return responses;
    }
}
