package io.redick.cloud.seata.order.service.impl;

import com.redick.util.LogUtil;
import io.redick.cloud.seata.order.entity.SeataOrder;
import io.redick.cloud.seata.order.feign.AccountApi;
import io.redick.cloud.seata.order.feign.StorageApi;
import io.redick.cloud.seata.order.mapper.SeataOrderMapper;
import io.redick.cloud.seata.order.service.SeataOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.seata.spring.annotation.GlobalTransactional;
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
 * @since 2023-06-02
 */
@Service
@Slf4j
@AllArgsConstructor
public class SeataOrderServiceImpl extends ServiceImpl<SeataOrderMapper, SeataOrder> implements SeataOrderService {

    private final AccountApi accountApi;

    private final StorageApi storageApi;

    private final  SeataOrderMapper seataOrderMapper;

    @Override
    @GlobalTransactional(name = "fsp-create-order", rollbackFor = Exception.class)
    public void create(SeataOrder seataOrder) {
        log.info(LogUtil.marker(), "交易开始");
        //本地方法
        seataOrderMapper.create(seataOrder);
        //远程方法 扣减库存
        storageApi.decrease(seataOrder.getProductId(), seataOrder.getCount());
        //远程方法 扣减账户余额
        log.info(LogUtil.marker(), "扣减账户开始order中");
        accountApi.decrease(seataOrder.getId(), seataOrder.getUserId(), seataOrder.getMoney());
        log.info(LogUtil.marker(), "扣减账户结束order中");
        log.info("交易结束");
    }

    @Override
    public void update(Long id, BigDecimal money, Integer status) {
        log.info("修改订单状态，入参为：id={}, money={}, status={}", id, money, status);
        seataOrderMapper.update(id, money, status);
    }
}
