package io.redick.cloud.order.controller;

import com.redick.annotation.LogMarker;
import io.redick.cloud.account.AccountService;
import io.redick.cloud.account.dto.StockDTO;
import io.redick.cloud.common.domain.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Redick01
 */
@RestController
@RequestMapping("/order")
@Api(tags = "订单")
public class OrderController {

    private final AccountService accountService;

    private final StreamBridge streamBridge;

    private final LoadBalancerClient loadBalancerClient;

    private final RestTemplate restTemplate;

    public OrderController(AccountService accountService, StreamBridge streamBridge,
                           LoadBalancerClient loadBalancerClient, RestTemplate restTemplate) {
        this.accountService = accountService;
        this.streamBridge = streamBridge;
        this.loadBalancerClient = loadBalancerClient;
        this.restTemplate = restTemplate;
    }

    @Value("${spring.application.name}")
    private String appName;

    @GetMapping("/accountList")
    @LogMarker(businessDescription = "/order/accountList")
    @ApiOperation(value = "获取库存列表")
    public R<List<StockDTO>> accountList() {
        return accountService.list();
    }

    @GetMapping("/stockList")
    @LogMarker(businessDescription = "/order/stockList")
    @ApiOperation(value = "获取库存列表")
    public R<List<StockDTO>> stockList() {
        ServiceInstance serviceInstance = loadBalancerClient.choose("account-svc");
        String url = String.format("http://%s:%s/account/list", serviceInstance.getHost(),serviceInstance.getPort());
        return restTemplate.getForObject(url, R.class);
    }

    @GetMapping("/echo")
    @LogMarker(businessDescription = "/order/echo")
    public String echo(){
        String url = String.format("http://%s:%s/account/echo/%s","account-svc", "8088", appName);
        return restTemplate.getForObject(url, String.class);
    }

    @GetMapping("/feignEcho")
    @LogMarker(businessDescription = "/order/feignEcho")
    public String feignEcho(){
        return accountService.echo(appName);
    }

    @GetMapping("/mqStock")
    @LogMarker(businessDescription = "/order/mqStock")
    @ApiOperation(value = "mq-发送数据")
    public R<?> mqStock() {
        StockDTO stockDTO = new StockDTO();
        stockDTO.setProductId("222");
        stockDTO.setProductName("手机112");
        stockDTO.setTotalCount(2000);
        Map<String, Object> headers = new HashMap<>();
        Message<StockDTO> msg = new GenericMessage<>(stockDTO, headers);
        streamBridge.send("producer-out-1", msg);
        return R.ok();
    }
}
