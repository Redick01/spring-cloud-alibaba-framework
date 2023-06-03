package io.redick.cloud.seata.storage.controller;

import com.redick.annotation.LogMarker;
import io.redick.cloud.common.domain.R;
import io.redick.cloud.seata.storage.service.SeataStockService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Redick01
 * @since 2023-06-02
 */
@RestController
@RequestMapping("/seataStock")
@AllArgsConstructor
public class SeataStockController {

    private final SeataStockService seataStockService;

    /**
     * 扣减库存
     *
     * @param productId 产品id
     * @param count     数量
     * @return {@link R}
     */
    @GetMapping("/decrease")
    @LogMarker(businessDescription = "扣减库存")
    public R<String> decrease(@RequestParam("productId") Long productId, @RequestParam("count") Integer count) {
        seataStockService.decrease(productId, count);
        return R.ok(null, "Decrease stock success");
    }
}
