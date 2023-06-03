package io.redick.cloud.seata.order.service;

import io.redick.cloud.seata.order.entity.SeataOrder;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Redick01
 * @since 2023-06-02
 */
public interface SeataOrderService extends IService<SeataOrder> {

    /**
     * 创建订单
     *
     * @param seataOrder 订单
     */
    void create(SeataOrder seataOrder);

    /**
     * 修改订单状态
     *
     * @param userId 用户id
     * @param money 钱
     * @param status 状态
     */
    void update(Long userId, BigDecimal money, Integer status);
}
