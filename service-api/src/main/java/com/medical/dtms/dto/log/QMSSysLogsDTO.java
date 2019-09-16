package com.medical.dtms.dto.log;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;
import lombok.Data;

import java.util.Date;

/**
 * @version： QMSSysLogsDTO.java v 1.0, 2019年08月14日 10:52 wuxuelin Exp$
 * @Description  系统操作日志
 **/
@Data
public class QMSSysLogsDTO {
    private Long id;

    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long bizId;

    private Integer operationType;

    private String tableName;

    private String businessName;

    private String objectId;

    private String operationIp;

    private String createDate;

    private String createUserId;

    private String createUserName;

    private Boolean isDeleted;

    private String creator;

    private String creatorId;

    private Date gmtCreated;

    private String modifier;

    private String modifierId;

    private Date gmtModified;
}