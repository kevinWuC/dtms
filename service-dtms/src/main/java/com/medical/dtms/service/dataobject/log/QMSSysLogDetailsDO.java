package com.medical.dtms.service.dataobject.log;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;
import lombok.Data;

import javax.persistence.Table;
import java.util.Date;

/**
 * @version： QMSSysLogDetailsDO.java v 1.0, 2019年08月14日 10:52 wuxuelin Exp$
 * @Description  系统操作日志明细
 **/
@Data
@Table(name = "tb_dtms_qms_sys_log_details")
public class QMSSysLogDetailsDO {
    private Long id;

    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long bizId;

    private String fieldName;

    private String fieldText;

    private String newValue;

    private String oldValue;

    private Boolean isDeleted;

    private String creator;

    private String creatorId;

    private Date gmtCreated;

    private String modifier;

    private String modifierId;

    private Date gmtModified;
}