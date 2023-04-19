package io.redick.cloud.account.controller;

import com.redick.annotation.LogMarker;
import io.redick.cloud.account.dto.AccountDto;
import io.redick.cloud.account.dto.AccountList;
import io.redick.cloud.account.dto.ListAccountResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
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
public class AccountController {

    @GetMapping(path = "/list")
    @LogMarker(businessDescription = "account#list")
    @ApiOperation(value = "Account#list", notes = "account")
    public ListAccountResponse list() {
        AccountList list = new AccountList();
        List<AccountDto> accounts = new ArrayList<>();
        AccountDto dto = new AccountDto();
        dto.setId("1");
        dto.setName("Redick");
        dto.setEmail("11090829@qq.com");
        accounts.add(dto);
        list.setAccounts(accounts);
        return new ListAccountResponse(list);
    }
}
