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

package io.redick.cloud.gary.holder;


/**
 * @author: Redick01
 * @date: 2023/6/7 20:01
 */
public class GrayRequestContextHolder {

    private static final ThreadLocal<Boolean> GARY_TAG = new ThreadLocal<>();

    public static void setGrayTag(final Boolean tag) {
        GARY_TAG.set(tag);
    }

    public static Boolean getGrayTag() {
        return GARY_TAG.get();
    }

    public static void remove() {
        GARY_TAG.remove();
    }
}
