package com.medical.dtms.common.model.table.query;

import lombok.Data;

/**
 * @version： DataBaseTableQuery.java v 1.0, 2019年08月21日 17:39 wuxuelin Exp$
 * @Description 数据库表查询实体类
 **/
@Data
public class DataBaseTableQuery {
    /**
     * 数据库名
     */
    private String dataBaseName;
    /**
     * 表名
     */
    private String tableName;
    /**
     * sql 语句
     */
    private String sql;
    /**
     * 执行类型  1 查询列表 2 更新与删除
     */
    private Integer operateType;
}
