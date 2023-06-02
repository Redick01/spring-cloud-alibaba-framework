package io.redick.cloud.seata.storage.mapper;

import io.redick.cloud.seata.storage.entity.SeataStock;
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
public interface SeataStockMapper extends BaseMapper<SeataStock> {

}
