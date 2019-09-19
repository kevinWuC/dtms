package com.medical.dtms.common.model.job;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;
import lombok.Data;

/**
 * @version： SimpleQMSJobsModel.java v 1.0, 2019年09月19日 10:42 wuxuelin Exp$
 * @Description 简易 职位实体类
 **/
@Data
public class SimpleQMSJobsModel {

    /**
     * 业务主键
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long jobId;
    /**
     * 职位名称
     */
    private String jobName;
}
