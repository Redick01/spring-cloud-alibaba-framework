package io.redick.cloud.account;

import io.redick.cloud.account.dto.ListAccountResponse;
import javax.validation.constraints.Min;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Redick01
 */
@FeignClient(name = AccountConstant.SERVICE_NAME, path = "/v1/account", url = "http://localhost:8088")
public interface AccountService {

    @GetMapping(path = "/list")
    ListAccountResponse listAccounts(@RequestParam int offset, @RequestParam @Min(0) int limit);

}
