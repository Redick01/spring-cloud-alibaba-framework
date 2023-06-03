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

package io.redick.cloud.seata.order.feign;

import io.redick.cloud.common.domain.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

/**
 * @author: Redick01
 * @date: 2023/6/3 21:18
 */
@FeignClient(value = "account-seata-svc")
public interface AccountApi {

    /**
     * 扣减账户余额
     *
     * @param id id
     * @param userId 用户id
     * @param money  金额
     * @return result
     */
    @GetMapping("/seataAccount/decrease")
    R<String> decrease(@RequestParam("id") Long id, @RequestParam("userId") Long userId, @RequestParam("money") BigDecimal money);
}
