package io.redick.cloud.common.controller;

import io.redick.cloud.common.constants.Constants;
import io.redick.cloud.common.domain.TableDataInfo;

import java.util.List;

/**
 * @author Redick01
 */
public class BaseController {

    protected TableDataInfo tableData(List<?> list) {

        return new TableDataInfo(Constants.SUCCESS, "success", list.size(), list);
    }
}
