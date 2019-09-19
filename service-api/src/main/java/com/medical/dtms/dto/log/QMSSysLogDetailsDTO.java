package com.medical.dtms.dto.log;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;
import lombok.Data;

import java.util.Date;

/**
 * @version： QMSSysLogDetailsDTO.java v 1.0, 2019年08月14日 10:52 wuxuelin Exp$
 * @Description 系统操作日志明细
 **/
@Data
public class QMSSysLogDetailsDTO {
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
     * 系统日志 id
     */
    private Long logId;
    /**
     * 字段名称
     */
    private String fieldName;
    /**
     * 字段值
     */
    private String fieldText;
    /**
     * 新值
     */
    private String newValue;
    /**
     * 旧值
     */
    private String oldValue;
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