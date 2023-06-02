package io.redick.cloud.seata.order.service.impl;

import io.redick.cloud.seata.order.entity.SeataOrder;
import io.redick.cloud.seata.order.mapper.SeataOrderMapper;
import io.redick.cloud.seata.order.service.SeataOrderService;
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
public class SeataOrderServiceImpl extends ServiceImpl<SeataOrderMapper, SeataOrder> implements SeataOrderService {

}
