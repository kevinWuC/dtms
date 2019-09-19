package com.medical.dtms.dto.log.query;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;
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
     * 结束时间
     */
    private Date endTime;
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
    /**
     * 系统日志id
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long logId;


}
