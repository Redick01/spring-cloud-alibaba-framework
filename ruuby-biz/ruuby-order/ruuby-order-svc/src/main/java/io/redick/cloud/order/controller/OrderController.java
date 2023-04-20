package io.redick.cloud.order.controller;

import com.redick.annotation.LogMarker;
import io.redick.cloud.account.AccountService;
import io.redick.cloud.account.dto.StockDTO;
import io.redick.cloud.common.domain.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author Redick01
 */
@RestController
@RequestMapping("/order")
@Api(tags = "订单")
public class OrderController {

    @Lazy
    @Autowired
    private AccountService accountService;

    @GetMapping("/accountList")
    @LogMarker(businessDescription = "/order/accountList")
    @ApiOperation(value = "获取库存列表")
    public R<List<StockDTO>> accountList() {
        return accountService.list();
    }

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/stockList")
    @LogMarker(businessDescription = "/order/stockList")
    @ApiOperation(value = "获取库存列表")
    public R<List<StockDTO>> stockList() {
        ServiceInstance serviceInstance = loadBalancerClient.choose("account-svc");
        String url = String.format("http://%s:%s/account/list", serviceInstance.getHost(),serviceInstance.getPort());
        return restTemplate.getForObject(url, R.class);
    }
}
