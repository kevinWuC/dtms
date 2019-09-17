package com.medical.dtms.dto.datasource.query;

import lombok.Data;

/**
 * @version： QMSTaskQuery.java v 1.0, 2019年09月17日 16:22 wuxuelin Exp$
 * @Description 定时任务查询类
 **/
@Data
public class QMSTaskQuery {
    /**
     * 定时自动备份执行时间
     */
    private Integer excDate;
    /**
     * 是否有效 1有效  0无效
     */
    private Boolean effective;
}
