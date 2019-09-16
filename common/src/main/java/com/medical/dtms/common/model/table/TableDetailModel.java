package com.medical.dtms.common.model.table;

import lombok.Data;

/**
 * @version： TableDetailModel.java v 1.0, 2019年08月22日 11:56 wuxuelin Exp$
 * @Description 数据库资源 - 数据库字段model
 **/
@Data
public class TableDetailModel {

    /**
     * 列名
     */
    private String columnName;
    /**
     * 数据类型
     */
    private String columnType;
    /**
     * 长度
     */
    private Integer columnLength;
    /**
     * 标识
     */
    private Integer identification;
    /**
     * 是否为主键 true 是 false 否
     */
    private Boolean primaryKey;
    /**
     * 是否为空 true 是 false 否
     */
    private Boolean allowEmpty;
    /**
     * 默认值
     */
    private String defaultValue;
    /**
     * 说明
     */
    private String remark;
}
