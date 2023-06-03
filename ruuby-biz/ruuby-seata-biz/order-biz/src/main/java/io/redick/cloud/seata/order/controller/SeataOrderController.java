package io.redick.cloud.seata.order.controller;

import com.redick.annotation.LogMarker;
import io.redick.cloud.common.domain.R;
import io.redick.cloud.seata.order.entity.SeataOrder;
import io.redick.cloud.seata.order.service.SeataOrderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Redick01
 * @since 2023-06-02
 */
@RestController
@RequestMapping("/seataOrder")
@AllArgsConstructor
public class SeataOrderController {

    private final SeataOrderService seataOrderService;

    /**
     * 创建订单
     *
     * @param seataOrder 订单
     * @return {@link SeataOrder}
     */
    @PostMapping("/create")
    @LogMarker(businessDescription = "创建订单")
    public R<SeataOrder> create(@RequestBody SeataOrder seataOrder) {
        seataOrderService.create(seataOrder);
        return R.ok(seataOrder, "Create order success");
    }

    /**
     * 修改订单状态
     *
     * @param id id
     * @param money money
     * @param status status
     * @return {@link R}
     */
    @RequestMapping("/update")
    @LogMarker(businessDescription = "修改订单状态")
    public R<String> update(@RequestParam("id") Long id, @RequestParam("money") BigDecimal money,
                  @RequestParam("status") Integer status) {
        seataOrderService.update(id, money, status);
        return R.ok(null, "订单状态修改成功");
    }
}
