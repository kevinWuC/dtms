package com.medical.dtms.dto.job.query;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;
import lombok.Data;

/**
 * @version： QMSJobsQuery.java v 1.0, 2019年08月23日 10:16 huangshuaiquan Exp$
 * @Description
 **/
@Data
public class QMSJobsQuery {

    /**
     * 业务主键
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long bizId;
    /**
     * 职位名称
     */
    private String jobName;
    /**
     * 当前页码
     */
    private Integer pageNo;
    /**
     * 每页展示数据条数
     */
    private Integer pageSize;

    public QMSJobsQuery() {
    }

    public QMSJobsQuery(Long bizId) {
        this.bizId = bizId;
    }

    public QMSJobsQuery(String jobName) {
        this.jobName = jobName;
    }

}
