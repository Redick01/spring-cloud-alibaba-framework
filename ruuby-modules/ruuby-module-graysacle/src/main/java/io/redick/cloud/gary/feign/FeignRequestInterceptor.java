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

package io.redick.cloud.gary.feign;

import feign.*;
import io.redick.cloud.gary.common.GrayConstant;
import io.redick.cloud.gary.holder.GrayRequestContextHolder;

import java.util.Collections;

/**
 * @author: Redick01
 * @date: 2023/6/21 19:35
 */
public class FeignRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        // 如果灰度标记为true，将灰度标记通过HttpHeader传递下去
        if (GrayRequestContextHolder.getGrayTag()) {
            template.header(GrayConstant.HEADER_GRAY_TAG, Collections.singleton(GrayConstant.HEADER_GRAY_TAG_VALUE));
        }
    }
}
