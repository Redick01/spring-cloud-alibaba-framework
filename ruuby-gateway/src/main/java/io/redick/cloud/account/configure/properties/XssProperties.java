package io.redick.cloud.account.configure.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Redick01
 */
@Configuration
@RefreshScope
@ConfigurationProperties("security.xss")
@Data
public class XssProperties {

    /**
     * Xss开关
     */
    private Boolean enabled;

    /**
     * 排除Xss检查的url列表
     */
    private List<String> excludeUrls = new ArrayList<>();
}
