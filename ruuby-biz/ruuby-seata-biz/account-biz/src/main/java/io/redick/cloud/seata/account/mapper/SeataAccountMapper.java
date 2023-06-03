package io.redick.cloud.seata.account.mapper;

import io.redick.cloud.seata.account.entity.SeataAccount;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Redick01
 * @since 2023-06-01
 */
@Mapper
public interface SeataAccountMapper extends BaseMapper<SeataAccount> {

    /**
     * 扣减账户余额
     *
     * @param userId 用户id
     * @param money  金额
     */
    void decrease(@Param("userId") Long userId, @Param("money") BigDecimal money);
}
