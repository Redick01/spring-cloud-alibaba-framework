package io.redick.cloud.seata.order.mapper;

import io.redick.cloud.seata.order.entity.SeataOrder;
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
 * @since 2023-06-02
 */
@Mapper
public interface SeataOrderMapper extends BaseMapper<SeataOrder> {

    /**
     * 创建订单
     *
     * @param seataOrder 订单
     */
    void create(SeataOrder seataOrder);

    /**
     * 修改订单金额
     *
     * @param id id
     * @param money 钱
     * @param status 状态
     */
    void update(@Param("id") Long id, @Param("money") BigDecimal money, @Param("status") Integer status);
}
