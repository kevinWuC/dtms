package com.medical.dtms.common.model.datasource;

import lombok.Data;

/**
 * $Id：UsageOfTablesModel.java v 1.0, 2019/9/12 1s3:26 wuxuelin Exp$
 *
 * @Description 数据库备份 - 表空间使用情况
 **/
@Data
public class UsageOfTablesModel {
    /** 表名*/
    private String tableName;
    /** 记录数*/
    private Integer countNo;
    /** 使用大小*/
    private String usageSum;
    /** 索引所使用大小*/
    private String indexUsageSum;
    /**未用的空间量*/
    private Integer emptySum;
    /** 表说明*/
    private String  tableContent;
}
