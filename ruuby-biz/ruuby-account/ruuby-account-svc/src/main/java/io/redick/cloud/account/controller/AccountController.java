package io.redick.cloud.account.controller;

import com.redick.annotation.LogMarker;
import io.redick.cloud.account.AccountService;
import io.redick.cloud.account.dto.StockDTO;
import io.redick.cloud.account.entity.Stock;
import io.redick.cloud.account.service.StockService;
import io.redick.cloud.common.domain.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Redick01
 */
@RestController
@Api(tags = "AccountController")
@RequestMapping("/account")
public class AccountController implements AccountService {

    @Autowired
    private StockService stockService;

    @GetMapping(path = "/list")
    @LogMarker(businessDescription = "account#list")
    @ApiOperation(value = "Account#list", notes = "account")
    @Override
    public R<List<StockDTO>> list() {
        List<StockDTO> accounts = new ArrayList<>();
        List<Stock> stockList = stockService.list();
        stockList.forEach(sl -> {
            StockDTO dto = new StockDTO();
            BeanUtils.copyProperties(sl, dto);
            accounts.add(dto);
        });
        return R.ok(accounts);
    }
}
