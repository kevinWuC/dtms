package com.medical.dtms.dto.log.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @version： QMSSysLoginLogQuery.java v 1.0, 2019年08月22日 10:15 huangshuaiquan Exp$
 * @Description
 **/
@Data
public class QMSSysLoginLogQuery {


    /**
     * 登录账户
     */
    private String account;
    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date beginTime;
    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;
    /**
     * 当前页码
     */
    private Integer pageNo;
    /**
     * 每页展示数据条数
     */
    private Integer pageSize;


    public QMSSysLoginLogQuery() {
    }

    public QMSSysLoginLogQuery(Date beginTime, Date endTime) {
        this.beginTime = beginTime;
        this.endTime = endTime;
    }

    public QMSSysLoginLogQuery(Date beginTime, Date endTime,String account) {
        this(beginTime, endTime);
        this.account = account;
    }
}
