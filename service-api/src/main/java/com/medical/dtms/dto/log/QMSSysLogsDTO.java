package com.medical.dtms.dto.log;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;
import lombok.Data;

import java.util.Date;

/**
 * @version： QMSSysLogsDTO.java v 1.0, 2019年08月14日 10:52 wuxuelin Exp$
 * @Description 系统操作日志
 **/
@Data
public class QMSSysLogsDTO {
    /**
     * 自增id
     */
    private Long id;
    /**
     * 业务主键
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long bizId;
    /**
     * 操作类型 1-新增 2-修改 3-删除 4-其他
     */
    private Integer operationType;
    /**
     * 数据库表
     */
    private String tableName;
    /**
     * 业务名称
     */
    private String businessName;
    /**
     * 对象主键
     */
    private String objectId;
    /**
     * 操作IP
     */
    private String operationIp;
    /**
     * 发生时间
     */
    private String createDate;
    /**
     * 0-未删除  1-删除
     */
    private Boolean isDeleted;
    /**
     * 创建人
     */
    private String creator;
    /**
     * 创建人id
     */
    private String creatorId;
    /**
     * 创建时间
     */
    private Date gmtCreated;
    /**
     * 修改人
     */
    private String modifier;
    /**
     * 修改人id
     */
    private String modifierId;
    /**
     * 修改时间
     */
    private Date gmtModified;
}