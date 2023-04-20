package io.redick.cloud.account.controller;

import com.redick.annotation.LogMarker;
import io.redick.cloud.account.AccountService;
import io.redick.cloud.account.dto.StockDTO;
import io.redick.cloud.common.domain.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    @GetMapping(path = "/list")
    @LogMarker(businessDescription = "account#list")
    @ApiOperation(value = "Account#list", notes = "account")
    @Override
    public R<List<StockDTO>> list() {
        List<StockDTO> accounts = new ArrayList<>();
        StockDTO dto = new StockDTO();
        dto.setProductId("111");
        dto.setProductName("手机");
        dto.setTotalCount(100);
        dto.setProductDesc("华为P100");
        dto.setCreateTime(new Date());
        accounts.add(dto);
        return R.ok(accounts);
    }
}
