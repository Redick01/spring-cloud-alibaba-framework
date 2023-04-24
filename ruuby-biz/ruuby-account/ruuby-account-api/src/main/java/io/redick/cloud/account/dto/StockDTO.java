package io.redick.cloud.account.dto;

import io.redick.cloud.common.domain.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author Redick01
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class StockDTO extends BaseRequest implements Serializable {

    private String productId;

    private Integer totalCount;

    private String productName;

    private Date beginTime;

    private Date endTime;

    private String productDesc;
}
