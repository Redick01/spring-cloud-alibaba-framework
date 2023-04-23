package io.redick.cloud.account.service.impl;

import io.redick.cloud.account.entity.Stock;
import io.redick.cloud.account.mapper.StockMapper;
import io.redick.cloud.account.service.StockService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.redick.cloud.datasource.annotation.Master;
import io.redick.cloud.datasource.annotation.Slave;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Redick01
 * @since 2023-04-23
 */
@Service
@Master
public class StockServiceImpl extends ServiceImpl<StockMapper, Stock> implements StockService {

}
