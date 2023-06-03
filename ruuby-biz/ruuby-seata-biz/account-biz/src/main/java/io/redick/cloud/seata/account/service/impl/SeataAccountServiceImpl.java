package io.redick.cloud.seata.account.service.impl;

import com.redick.util.LogUtil;
import io.redick.cloud.common.domain.R;
import io.redick.cloud.seata.account.entity.SeataAccount;
import io.redick.cloud.seata.account.feign.OrderApi;
import io.redick.cloud.seata.account.mapper.SeataAccountMapper;
import io.redick.cloud.seata.account.service.SeataAccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Redick01
 * @since 2023-06-01
 */
@Service
@Slf4j
@AllArgsConstructor
public class SeataAccountServiceImpl extends ServiceImpl<SeataAccountMapper, SeataAccount> implements SeataAccountService {

    private final SeataAccountMapper seataAccountMapper;

    private final OrderApi orderApi;

    @Override
    public void decrease(Long id, Long userId, BigDecimal money) {
        log.info(LogUtil.marker(), "扣减账户开始account中");
        //模拟超时异常，全局事务回滚
                try {
                    Thread.sleep(61*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        seataAccountMapper.decrease(userId, money);
        log.info(LogUtil.marker(), "扣减账户结束account中");

        //修改订单状态，此调用会导致调用成环
        log.info(LogUtil.marker(), "修改订单状态开始");
        R<String> r = orderApi.update(id, money.multiply(new BigDecimal("0.09")), 0);
        log.info(LogUtil.marker(r), "修改订单状态结束");
    }
}
