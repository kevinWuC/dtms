package com.medical.dtms.dto.log.query;

import lombok.Data;

/**
 * @version： QMSSysLogsQuery.java v 1.0, 2019年08月19日 16:12 wuxuelin Exp$
 * @Description 系统操作日志 查询实体类
 **/
@Data
public class QMSSysLogsQuery {

    /**
     * 当前页码
     */
    private Integer pageNo;
    /**
     * 每页展示数据条数
     */
    private Integer pageSize;
}
