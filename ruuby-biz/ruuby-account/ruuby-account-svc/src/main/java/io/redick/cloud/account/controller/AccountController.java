package io.redick.cloud.account.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.redick.annotation.LogMarker;
import io.redick.cloud.account.AccountService;
import io.redick.cloud.account.dto.StockDTO;
import io.redick.cloud.account.entity.Stock;
import io.redick.cloud.account.service.StockService;
import io.redick.cloud.common.controller.BaseController;
import io.redick.cloud.common.domain.R;
import io.redick.cloud.common.domain.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Redick01
 */
@RestController
@Api(tags = "AccountController")
@RequestMapping("/account")
public class AccountController extends BaseController implements AccountService {

    @Autowired
    private StockService stockService;

    @GetMapping(path = "/list")
    @LogMarker(businessDescription = "account#list")
    @ApiOperation(value = "Account#list", notes = "account")
    @Override
    public R<List<StockDTO>> list() {
        List<StockDTO> accounts = new ArrayList<>();
        List<Stock> stockList = stockService.list();
        stockList.forEach(sl -> {
            StockDTO dto = new StockDTO();
            BeanUtils.copyProperties(sl, dto);
            accounts.add(dto);
        });
        return R.ok(accounts);
    }

    @GetMapping("/pages")
    @LogMarker(businessDescription = "库存分页数据")
    @ApiOperation(value = "库存分页列表", notes = "page")
    public TableDataInfo page(StockDTO stockDTO) {
        Page<Stock> page = new Page<>(stockDTO.getPageIndex(), stockDTO.getPageSize());
        Wrapper<Stock> wrapper = Stock.pageWrapper(page, stockDTO);
        IPage<Stock> iPage = stockService.page(page, wrapper);
        List<Stock> stockList = iPage.getRecords();
        return new TableDataInfo(stockList, stockList.size());
    }

    @PostMapping("/save")
    @LogMarker(businessDescription = "保存库存")
    @ApiOperation(value = "保存库存", notes = "Save")
    public @ResponseBody R<?> save(@RequestBody StockDTO stockDTO) {
        Stock stock = new Stock();
        stock.setProductId(stockDTO.getProductId());
        stock.setProductName(stockDTO.getProductName());
        stock.setTotalCount(stockDTO.getTotalCount());
        stock.setProductDesc(stockDTO.getProductDesc());
        stock.setCreateTime(new Date());
        stockService.save(stock);
        return R.ok();
    }
}
