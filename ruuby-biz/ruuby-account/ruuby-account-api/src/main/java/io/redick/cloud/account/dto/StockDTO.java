package io.redick.cloud.account.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author Redick01
 */
@Data
public class StockDTO implements Serializable {

    private String productId;

    private Integer totalCount;

    private String productName;

    private Date createTime;

    private String productDesc;
}
