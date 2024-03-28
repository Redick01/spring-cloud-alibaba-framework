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

import com.google.common.collect.Maps;
import com.redick.annotation.LogMarker;
import com.redick.util.LogUtil;
import io.redick.monitor.business.CollectorHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author: Redick01
 * @date: 2024/3/15 15:31
 */
@RestController
@RequestMapping("/monitor")
@Slf4j
public class MonitorController {

    @Autowired
    private CollectorHandler collectorHandler;

    @GetMapping(path = "/echo/{business}")
    @LogMarker(businessDescription = "健康监测")
    public Map<String, String> monitor(@PathVariable String business) {
        Map<String, String> responses = Maps.newHashMap();
        if (StringUtils.isBlank(business)) {
            log.debug(LogUtil.marker(), "参数不可为空");
            responses.put("code", "0001");
        } else {
            // 解析参数
            try {
                String[] strings = business.split(",");
                for (String string : strings) {
                    String status = collectorHandler.doCollect(string);
                    responses.put(string, status);
                }
                responses.put("code", "0000");
            } catch (Exception e) {
                log.error(LogUtil.exceptionMarker(), "异常", e);
                responses.put("code", "0002");
            }
        }
        return responses;
    }
}
