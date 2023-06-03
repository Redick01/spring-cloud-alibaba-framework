package io.redick.cloud.seata.storage.mapper;

import io.redick.cloud.seata.storage.entity.SeataStock;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Redick01
 * @since 2023-06-02
 */
@Mapper
public interface SeataStockMapper extends BaseMapper<SeataStock> {

    /**
     * 扣减库存
     *
     * @param productId 产品id
     * @param count 数量
     */
    void decrease(@Param("productId") Long productId, @Param("count") Integer count);
}
