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

import com.redick.annotation.LogMarker;
import io.redick.cloud.common.domain.R;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 * @author: Redick01
 * @date: 2024/5/13 11:09
 */
@RestController
@RequestMapping("/code")
public class StatisticsController {

    @PostMapping("/statistics")
    @LogMarker(businessDescription = "从git统计代码行数")
    public @ResponseBody R<CodeStatistics> statistics(@RequestBody GitInfo gitInfo) {

        Path tempDir = null;
        int count = 0;
        if (!gitInfo.volidate()) {
            return R.fail(0001, "参数为空");
        }
        try {
            // 创建临时目录
            tempDir = Files.createTempDirectory("git-clone-");
            File localPath = tempDir.toFile();

            // 克隆仓库
            System.out.println("Cloning repository...");
            Git.cloneRepository()
                    .setURI(getRealUrl(gitInfo.getGitUrl(), gitInfo.getUsername(), gitInfo.getPassword()))
                    .setDirectory(localPath)
                    .setBranch(gitInfo.getBranch())
                    .call();

            // 统计代码行数
            System.out.println("Counting lines of code...");
            count = countLinesInDirectory(localPath.toPath(), gitInfo.getPostfix());
            System.out.println("Total lines of code: " + count);

        } catch (IOException | GitAPIException e) {
            e.printStackTrace();
        } finally {
            // 清理：删除临时目录
            if (tempDir != null) {
                try (Stream<Path> walk = Files.walk(tempDir)) {
                    walk.sorted(Comparator.reverseOrder())
                            .map(Path::toFile)
                            .forEach(File::delete);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return R.ok(new CodeStatistics(count));
    }

    private static int countLinesInDirectory(Path directory, String flag) throws IOException {
        AtomicInteger count = new AtomicInteger();
        try (Stream<Path> paths = Files.walk(directory)) {
            paths.forEach(path -> {
                if (!Files.isDirectory(path)) {
                    try {
                        String postfix = getFileExtension(path);
                        if (StringUtils.isNotBlank(postfix) && flag.equals(postfix)) {
                            count.addAndGet(countLinesInFile(path));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        return count.get();
    }

    private static int countLinesInFile(Path file) throws IOException {
        return (int) Files.lines(file).count();
    }

    /**
     * 从给定的Path中获取文件扩展名（不包括'.')
     * @param path 文件路径
     * @return 文件扩展名，如果没有扩展名则返回null
     */
    private static String getFileExtension(Path path) {
        if (path == null || !path.getFileName().toString().contains(".")) {
            return null;
        }
        String fileName = path.getFileName().toString();
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex == -1 || dotIndex == fileName.length() - 1) {
            // 没有找到'.' 或者 '.'位于文件名末尾，没有扩展名
            return null;
        }
        return fileName.substring(dotIndex + 1);
    }

    private static String getRealUrl(String url, String username, String password) {
        // 要插入的字符串
        String stringToInsert = username + ":" + password + "@";
        // 插入的位置
        int position = 7;

        // 创建StringBuilder对象
        StringBuilder stringBuilder = new StringBuilder(url);

        // 在指定位置插入字符串
        stringBuilder.insert(position, stringToInsert);

        // 输出结果
        return stringBuilder.toString();
    }

    private static void te() {

        ok:
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.println("i=" + i + ",j=" + j);
                if (j == 5) {
                    break ok;
                }
            }
        }
        new Thread().start();
    }
}

