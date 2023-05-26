package io.redick.cloud.order.producer;

import io.redick.cloud.account.dto.StockDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.function.Supplier;

/**
 * @author Redick01
 */
@Configuration
@Slf4j
public class ProducerConfiguration {

//    @Bean
//    public Supplier<Flux<StockDTO>> producer() {
//        return () -> Flux.interval(Duration.ofSeconds(2)).map(s -> {
//            StockDTO stockDTO = new StockDTO();
//            stockDTO.setProductId("123");
//            stockDTO.setProductName("手机");
//            stockDTO.setTotalCount(1000);
//            return stockDTO;
//        }).log();
//    }
}
