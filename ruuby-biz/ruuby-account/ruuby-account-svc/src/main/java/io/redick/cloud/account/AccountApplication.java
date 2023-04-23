package io.redick.cloud.account;

import com.redick.starter.annotation.LogHelperEnable;
import io.redick.cloud.swagger.annotation.EnableRuubySwagger;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Redick01
 */
@SpringBootApplication
@EnableDiscoveryClient
@LogHelperEnable
@EnableRuubySwagger
@MapperScan("io.redick.cloud.account.mapper")
public class AccountApplication {

    public static void main( String[] args ) {
        SpringApplication.run(AccountApplication.class, args);
    }
}