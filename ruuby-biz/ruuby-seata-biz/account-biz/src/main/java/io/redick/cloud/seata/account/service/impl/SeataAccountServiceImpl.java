package io.redick.cloud.seata.account.service.impl;

import io.redick.cloud.seata.account.entity.SeataAccount;
import io.redick.cloud.seata.account.mapper.SeataAccountMapper;
import io.redick.cloud.seata.account.service.SeataAccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Redick01
 * @since 2023-06-01
 */
@Service
public class SeataAccountServiceImpl extends ServiceImpl<SeataAccountMapper, SeataAccount> implements SeataAccountService {

}
