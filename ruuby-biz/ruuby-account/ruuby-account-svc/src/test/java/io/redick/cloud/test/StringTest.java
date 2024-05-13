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

package io.redick.cloud.test;

/**
 * @author: Redick01
 * @date: 2024/5/13 10:49
 */
public class StringTest {
    public static void main(String[] args) {
        // 原始字符串
        String originalString = "Hello, World!";
        // 要插入的字符串
        String stringToInsert = "beautiful ";
        // 插入的位置
        int position = 7;

        // 创建StringBuilder对象
        StringBuilder stringBuilder = new StringBuilder(originalString);

        // 在指定位置插入字符串
        stringBuilder.insert(position, stringToInsert);

        // 输出结果
        System.out.println(stringBuilder.toString());  // 输出: Hello, beautiful World!
    }
}
