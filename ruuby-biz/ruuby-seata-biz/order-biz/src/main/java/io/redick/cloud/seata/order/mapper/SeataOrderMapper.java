package io.redick.cloud.seata.order.mapper;

import io.redick.cloud.seata.order.entity.SeataOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

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

}
