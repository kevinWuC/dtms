package com.medical.dtms.common.model.table;

import lombok.Data;

import java.util.List;

/**
 * @version： DataBaseTableModel.java v 1.0, 2019年08月21日 17:33 wuxuelin Exp$
 * @Description 数据库表 model 类
 **/
@Data
public class DataBaseTableModel {

    /**
     * 数据库名
     */
    private String dataBaseName;
    /**
     * 表名 集合
     */
    private List<String> tableNameList;
}
