package io.redick.cloud.account.consumer;

import com.redick.util.LogUtil;
import io.redick.cloud.account.dto.StockDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

/**
 * @author Redick01
 */
@Configuration
@Slf4j
public class ConsumerConfiguration {

    @Bean
    public Consumer<StockDTO> consumer() {
        return stockDTO -> {
            log.info(LogUtil.marker(stockDTO), "收到消息");
        };
    }
}
