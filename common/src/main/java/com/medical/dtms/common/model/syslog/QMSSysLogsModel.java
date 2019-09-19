package com.medical.dtms.common.model.syslog;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;
import lombok.Data;

import java.util.Date;

/**
 * @version： QMSSysLogsModel.java v 1.0, 2019年08月19日 15:56 wuxuelin Exp$
 * @Description
 **/
@Data
public class QMSSysLogsModel {
    /**
     * 业务主键
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long bizId;
    /**
     * 操作时间
     */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date operateDate;
    /**
     * 操作类型
     */
    private Integer operationType;
    /**
     * 操作类型
     */
    private String operationName;
    /**
     * 操作数据表
     */
    private String tableName;
    /**
     * 对象主键值
     */
    private String domainKey;
    /**
     * 操作模块
     */
    private String businessName;
    /**
     * IP 地址
     */
    private String operationIp;
    /**
     * 操作用户
     */
    private String operationUser;
}
