package io.redick.cloud.seata.storage.service.impl;

import io.redick.cloud.seata.storage.entity.SeataStock;
import io.redick.cloud.seata.storage.mapper.SeataStockMapper;
import io.redick.cloud.seata.storage.service.SeataStockService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class SeataStockServiceImpl extends ServiceImpl<SeataStockMapper, SeataStock> implements SeataStockService {

}
