package com.medical.dtms.common.model.syslog;

import lombok.Data;

/**
 * @version： QMSSysLogDetailsModel.java v 1.0, 2019年09月19日 21:25 wuxuelin Exp$
 * @Description 系统操作日志明细model
 **/
@Data
public class QMSSysLogDetailsModel {
    /**
     * 字段名称
     */
    private String fieldName;
    /**
     * 字段值
     */
    private String fieldText;
    /**
     * 新值
     */
    private String newValue;
    /**
     * 旧值
     */
    private String oldValue;
}
