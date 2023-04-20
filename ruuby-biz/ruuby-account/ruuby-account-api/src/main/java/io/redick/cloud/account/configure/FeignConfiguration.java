package io.redick.cloud.account.configure;

import io.redick.cloud.account.callback.AccountCallback;
import org.springframework.context.annotation.Bean;

/**
 * @author Redick01
 */
public class FeignConfiguration {

    @Bean
    public AccountCallback accountCallback() {
        return new AccountCallback();
    }
}
