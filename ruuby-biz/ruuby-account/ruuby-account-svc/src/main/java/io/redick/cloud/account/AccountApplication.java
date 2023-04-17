package io.redick.cloud.account;

import com.redick.starter.annotation.LogHelperEnable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Redick01
 */
@SpringBootApplication
@EnableDiscoveryClient
@LogHelperEnable
public class AccountApplication {

    public static void main( String[] args ) {
        SpringApplication.run(AccountApplication.class, args);
    }
}