package com.medical.dtms.common.model.train;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @version： TrainConfigStatementModel.java v 1.0, 2019年09月03日 14:17 huangshuaiquan Exp$
 * @Description
 **/
@Data
public class TrainConfigStatementModel {

    /**
     * 培训类型ID
     **/
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long trainTypeId;
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
     * 培训文件列表
     **/
    private List<TrainFileModel> listFile;
    /**
     * 及格分数
     **/
    private Integer passPoint;

    /**
     * 应培训时数
     **/
    private Integer readFen;
    /**
     * 考试名称
     **/
    private String examName;
    /**
     * 培训用户姓名串串
     **/
    private String trainUserNames;

}
