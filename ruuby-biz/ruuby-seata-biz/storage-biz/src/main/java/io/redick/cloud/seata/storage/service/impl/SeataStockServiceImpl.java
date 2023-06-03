package io.redick.cloud.seata.storage.service.impl;

import com.redick.util.LogUtil;
import io.redick.cloud.seata.storage.entity.SeataStock;
import io.redick.cloud.seata.storage.mapper.SeataStockMapper;
import io.redick.cloud.seata.storage.service.SeataStockService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
public class SeataStockServiceImpl extends ServiceImpl<SeataStockMapper, SeataStock> implements SeataStockService {

    private final SeataStockMapper seataStockMapper;

    @Override
    public void decrease(Long productId, Integer count) {
        log.info(LogUtil.marker(), "开始扣减库存");
        seataStockMapper.decrease(productId, count);
        log.info(LogUtil.marker(), "结束扣减库存");
    }
}
