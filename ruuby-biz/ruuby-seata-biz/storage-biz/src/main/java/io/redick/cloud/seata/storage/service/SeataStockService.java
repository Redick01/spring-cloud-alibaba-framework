package io.redick.cloud.seata.storage.service;

import io.redick.cloud.seata.storage.entity.SeataStock;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Redick01
 * @since 2023-06-02
 */
public interface SeataStockService extends IService<SeataStock> {

    /**
     * 扣减库存
     *
     * @param productId 产品ID
     * @param count 数量
     */
    void decrease(Long productId, Integer count);
}
