package io.redick.cloud.common.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Redick01
 */
@Data
public class BaseRequest implements Serializable {

    private static final long serialVersionUID = -1316467348784145982L;

    /**
     * 当前页索引
     */
    private Integer pageIndex;

    /**
     * 每页现实记录数
     */
    private Integer pageSize;

    /**
     * 排序字段
     */
    private String orderByColumn = "";

    /**
     * 排序方向
     */
    private String asc = "asc";

    public BaseRequest() {
        this.pageIndex = 0;
        this.pageSize = 10;

    }

    public BaseRequest(Integer pageIndex, Integer pageSize) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

    public BaseRequest(Integer pageIndex, Integer pageSize, String orderByColumn) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.orderByColumn = orderByColumn;
    }

    public BaseRequest(Integer pageIndex, Integer pageSize, String orderByColumn, String asc) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.orderByColumn = orderByColumn;
        this.asc = asc;
    }
}
