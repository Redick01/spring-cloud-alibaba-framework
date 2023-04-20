package io.redick.cloud.order;

import com.redick.starter.annotation.LogHelperEnable;
import io.redick.cloud.swagger.annotation.EnableRuubySwagger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Redick01
 */
@EnableRuubySwagger
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"io.redick.cloud.account"})
@LogHelperEnable
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }
}
