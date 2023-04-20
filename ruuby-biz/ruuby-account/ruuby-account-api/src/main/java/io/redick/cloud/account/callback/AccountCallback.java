package io.redick.cloud.account.callback;

import io.redick.cloud.account.AccountService;
import io.redick.cloud.account.dto.StockDTO;
import io.redick.cloud.common.constants.Constants;
import io.redick.cloud.common.domain.R;

import java.util.List;

/**
 * @author Redick01
 */
public class AccountCallback implements AccountService {

    @Override
    public R<List<StockDTO>> list() {
        return R.fail(null, Constants.FLOW, "Sentinel circuit breaker!");
    }
}
