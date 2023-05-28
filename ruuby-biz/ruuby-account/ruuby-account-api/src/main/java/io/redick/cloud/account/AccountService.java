package io.redick.cloud.account;

import io.redick.cloud.account.callback.AccountCallback;
import io.redick.cloud.account.configure.FeignConfiguration;
import io.redick.cloud.account.dto.StockDTO;
import io.redick.cloud.common.domain.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author Redick01
 */
@FeignClient(name = AccountConstant.SERVICE_NAME, path = "/account", fallback = AccountCallback.class,
        configuration = FeignConfiguration.class)
public interface AccountService {

    /**
     * 库存列表
     * @return 列表
     */
    @GetMapping(path = "/list")
    R<List<StockDTO>> list();

    /**
     * 注：OpenFeign+@PathVariable 需要指定value否则会报错
     *
     * @param string String
     * @return String
     */
    @GetMapping(path = "/echo/{string}")
    String echo(@PathVariable(value="string") String string);
}
