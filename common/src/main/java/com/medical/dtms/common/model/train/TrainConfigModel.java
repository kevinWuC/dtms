package com.medical.dtms.common.model.train;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;
import lombok.Data;

import java.util.Date;

/**
 * @version： TrainConfigModel.java v 1.0, 2019年08月29日 13:49 huangshuaiquan Exp$
 * @Description
 **/
@Data
public class TrainConfigModel {

    /**
     * 业务主键
     **/
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long bizId;
    /**
     * 培训类型名称
     **/
    private String trainTypeName;
    /**
     * 培训名称
     **/
    private String trainName;
    /**
     * 培训内容
     **/
    private String trainDescription;
    /**
     * 开始日期
     **/
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date startDate;
    /**
     * 结束日期
     **/
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date endDate;
    /**
     * 是否启用 false-不启用 true-启用
     **/
    private Boolean isStart;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtCreated;

}
