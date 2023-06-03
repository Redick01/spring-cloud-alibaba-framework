package io.redick.cloud.seata.account.controller;

import com.redick.annotation.LogMarker;
import io.redick.cloud.common.domain.R;
import io.redick.cloud.seata.account.service.SeataAccountService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Redick01
 * @since 2023-06-01
 */
@RestController
@RequestMapping("/seataAccount")
@AllArgsConstructor
public class SeataAccountController {

    private final SeataAccountService seataAccountService;

    /**
     * 扣减账户余额
     *
     * @param id id
     * @param userId 用户id
     * @param money  金额
     * @return
     */
    @RequestMapping("decrease")
    @LogMarker(businessDescription = "扣减账户余额")
    public R<String> decrease(@RequestParam("id") Long id, @RequestParam("userId") Long userId, @RequestParam("money") BigDecimal money) {
        seataAccountService.decrease(id, userId, money);
        return R.ok(null, "Account decrease success");
    }
}
