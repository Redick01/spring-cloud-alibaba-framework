package io.redick.cloud.common.domain;

import io.redick.cloud.common.constants.Constants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author Redick01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableDataInfo implements Serializable {

    private static final long serialVersionUID = 6049641946475510105L;

    private Integer code;

    private String msg;

    /**
     * 数据记录数
     */
    private Integer total;

    /**
     * 列表数据
     */
    private List<?> rows;

    public TableDataInfo(List<?> list, int total) {
        this.code = Constants.SUCCESS;
        this.msg = "success";
        this.rows = list;
        this.total = total;
    }
}
