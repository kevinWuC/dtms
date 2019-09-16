package com.medical.dtms.dto.datasource.query;

import lombok.Data;

/**
 * @version： QMSBackUpQuery.java v 1.0, 2019年09月16日 18:58 wuxuelin Exp$
 * @Description 查询
 **/
@Data
public class QMSBackUpQuery {

    /**
     * 当前页码
     */
    private Integer pageNo;
    /**
     * 每页展示数据条数
     */
    private Integer pageSize;
}
