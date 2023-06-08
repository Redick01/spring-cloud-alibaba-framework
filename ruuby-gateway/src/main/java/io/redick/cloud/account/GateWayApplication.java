package io.redick.cloud.account;

import io.redick.cloud.gary.configure.LoadBalancerGrayAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;

/**
 * @author Redick01
 */
@SpringBootApplication
@EnableDiscoveryClient
@LoadBalancerClients(defaultConfiguration = {LoadBalancerGrayAutoConfiguration.class})
public class GateWayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GateWayApplication.class, args);
    }
}