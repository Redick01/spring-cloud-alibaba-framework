package io.redick.cloud.account;

import com.redick.starter.annotation.LogHelperEnable;
import io.redick.cloud.swagger.annotation.EnableRuubySwagger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import javax.annotation.PostConstruct;

/**
 * @author Redick01
 */
@SpringBootApplication
@EnableDiscoveryClient
@LogHelperEnable
@EnableRuubySwagger
public class AccountApplication {

    @Value("${server.port}")
    private String port;

    @PostConstruct
    public void init() {
        System.out.println("port: " + port);
    }

    public static void main( String[] args ) {
        SpringApplication.run(AccountApplication.class, args);
    }
}