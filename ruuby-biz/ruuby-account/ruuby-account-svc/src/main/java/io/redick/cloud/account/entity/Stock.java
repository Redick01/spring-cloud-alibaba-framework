package io.redick.cloud.account.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.redick.cloud.account.dto.StockDTO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author Redick01
 * @since 2023-04-23
 */
@TableName("stock")
@Getter
@Setter
public class Stock implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 商品id
     */
    private String productId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 总数
     */
    private Integer totalCount;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 商品描述
     */
    private String productDesc;

    public static Wrapper<Stock> pageWrapper(Page<Stock> page, StockDTO stockDTO) {
        Wrapper<Stock> wrapper = Wrappers.<Stock>lambdaQuery();
        if (StringUtils.isNotBlank(stockDTO.getProductId())) {
            wrapper = Wrappers.<Stock>lambdaQuery().eq(Stock::getProductId, stockDTO.getProductId());
        }
        if (StringUtils.isNotBlank(stockDTO.getProductName())) {
            wrapper = Wrappers.<Stock>lambdaQuery().like(Stock::getProductName, stockDTO.getProductName());
        }
        if (StringUtils.isNotBlank(stockDTO.getProductDesc())) {
            wrapper = Wrappers.<Stock>lambdaQuery().like(Stock::getProductDesc, stockDTO.getProductDesc());
        }

        if (null != stockDTO.getBeginTime() && null != stockDTO.getEndTime()) {
            wrapper = Wrappers.<Stock>lambdaQuery().between(Stock::getCreateTime, stockDTO.getBeginTime(), stockDTO.getEndTime());
        }

        if (null != stockDTO.getBeginTime() && null == stockDTO.getEndTime()) {
            wrapper = Wrappers.<Stock>lambdaQuery().ge(Stock::getCreateTime, stockDTO.getBeginTime());
        }

        if (null == stockDTO.getBeginTime() && null != stockDTO.getEndTime()) {
            wrapper = Wrappers.<Stock>lambdaQuery().le(Stock::getCreateTime, stockDTO.getEndTime());
        }
        if (StringUtils.isNotBlank(stockDTO.getOrderByColumn()) && "asc".equalsIgnoreCase(stockDTO.getAsc())) {
            page.addOrder(OrderItem.asc(stockDTO.getOrderByColumn()));
        }
        if (StringUtils.isNotBlank(stockDTO.getOrderByColumn()) && "desc".equalsIgnoreCase(stockDTO.getAsc())) {
            page.addOrder(OrderItem.desc(stockDTO.getOrderByColumn()));
        }
        return wrapper;
    }

    @Override
    public String toString() {
        return "Stock{" +
        "id=" + id +
        ", productId=" + productId +
        ", productName=" + productName +
        ", totalCount=" + totalCount +
        ", createTime=" + createTime +
        ", productDesc=" + productDesc +
        "}";
    }
}
