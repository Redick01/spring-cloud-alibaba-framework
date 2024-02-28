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

package io.redick.cloud.account.utils;

import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.Timer;
import io.netty.util.TimerTask;

import java.util.concurrent.TimeUnit;

/**
 * @author: Redick01
 * @date: 2023/8/3 17:45
 */
public class HashedWheelTimerDemo {

    private volatile Timer failTimer = null;

    public static void main(String[] args) {
        new HashedWheelTimerDemo().invoke();
    }

    public void invoke() {

        try {
            doInvoke();
        } catch (Exception e) {
            System.out.println("调用doInvoke方法失败，5s后将进入后台的自动重试，异常信息：" + e.getMessage());
            addFailed(this::doInvoke);
        }
    }

    public void doInvoke() {
        // 这里让这个方法故意失败
        throw new RuntimeException("故意抛出异常");
    }

    private void addFailed(Runnable runnable) {
        if (null == failTimer) {
            synchronized (this) {
                if (failTimer == null) {
                    failTimer = new HashedWheelTimer();
                }
            }
        }
        RetryTimerTask retryTimerTask = new RetryTimerTask(3, 5, runnable);
        try {
            failTimer.newTimeout(retryTimerTask, 5, TimeUnit.SECONDS);
        } catch (Throwable e) {
            System.out.println("提交定时任务失败：" + e);
        }
    }

    static class RetryTimerTask implements TimerTask {

        /**
         * 每个几秒执行一次
         */
        private final long tick;

        /**
         * 最大重试次数
         */
        private final int retryCount;

        private int retryTimes = 0;

        private Runnable task;

        public RetryTimerTask(long tick, int retryCount, Runnable task) {
            this.tick = tick;
            this.retryCount = retryCount;
            this.task = task;
        }

        @Override
        public void run(Timeout timeout) throws Exception {
            try {
                task.run();
            } catch (Throwable e) {
                if ((++retryTimes >= retryCount)) {
                    System.out.println("超过最大重试次数，不在重试");
                    System.exit(0);
                } else {
                    System.out.println("重试失败，继续重试");
                    rePut(timeout);
                }
            }
        }

        private void rePut(Timeout timeout) {
            if (timeout == null) {
                return;
            }
            Timer timer = timeout.timer();
            if (timeout.isCancelled()) {
                return;
            }
            timer.newTimeout(timeout.task(), tick, TimeUnit.SECONDS);
        }
    }
}
