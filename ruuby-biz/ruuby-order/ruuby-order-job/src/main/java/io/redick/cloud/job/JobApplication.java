package io.redick.cloud.job;

import com.redick.starter.annotation.LogHelperEnable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Redick01
 */
@SpringBootApplication
@LogHelperEnable
public class JobApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobApplication.class, args);
    }
}
