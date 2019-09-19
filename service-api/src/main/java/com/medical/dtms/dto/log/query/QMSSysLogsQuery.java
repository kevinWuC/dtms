package com.medical.dtms.dto.log.query;

import lombok.Data;

import java.util.Date;

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
    /**
     * 开始时间
     */
    private Date beginTime;

    /**
     * 操作类型
     */
    private Integer operationType;
    /**
     * 操作模块
     */
    private String businessName;
    /**
     * 操作用户
     */
    private String operationUser;


}
