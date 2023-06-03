package io.redick.cloud.seata.account.service;

import io.redick.cloud.seata.account.entity.SeataAccount;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Redick01
 * @since 2023-06-01
 */
public interface SeataAccountService extends IService<SeataAccount> {

    /**
     * 扣减账户余额
     *
     * @param id id
     * @param userId 用户id
     * @param money  金额
     */
    void decrease(Long id, Long userId, BigDecimal money);
}
