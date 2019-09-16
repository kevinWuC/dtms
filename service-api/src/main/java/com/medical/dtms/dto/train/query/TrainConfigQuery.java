package com.medical.dtms.dto.train.query;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;
import lombok.Data;

/**
 * @version： TrainConfigQuery.java v 1.0, 2019年08月29日 10:46 huangshuaiquan Exp$
 * @Description
 **/
@Data
public class TrainConfigQuery {

    /**
     * 业务主键
     **/
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long bizId;
    /**
     * 培训名称
     **/
    private String trainName;
    /**
     * 当前页码
     */
    private Integer pageNo;
    /**
     * 每页展示数据条数
     */
    private Integer pageSize;

    public TrainConfigQuery() {
    }

    public TrainConfigQuery(String trainName) {
        this.trainName = trainName;
    }


}
