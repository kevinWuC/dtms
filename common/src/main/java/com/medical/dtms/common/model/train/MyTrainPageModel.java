package com.medical.dtms.common.model.train;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;
import lombok.Data;

import java.util.Date;

/**
 * @version： MyTrainPageModel.java v 1.0, 2019年10月11日 11:05 wuxuelin Exp$
 * @Description 我的培训 - 列表展示model
 **/
@Data
public class MyTrainPageModel {
    /**
     * 培训id
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long trainId;
    /**
     * 培训名称
     */
    private String trainName;
    /**
     * 培训类别id
     */
    private Long trainTypeId;
    /**
     * 培训类别名称
     */
    private String trainTypeName;
    /**
     * 是否完成文件阅读
     */
    private Boolean isFinishFile;
    /**
     * 阅读用时
     */
    private Date readTime;
    /**
     * 是否完成考试
     */
    private Boolean isFinishQuestions;
    /**
     * 考试完成时间
     */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date endDate;
    /**
     * 考试成绩
     */
    private String pointStr;
    /**
     * 及格分数
     */
    private Integer passPoint;
    /**
     * 培训结论：是否及格 true 是 false 否
     */
    private Boolean pass;
    private String passStr;
    /**
     * 培训状态：-1未开始0正进行1已完成
     */
    private Integer state;
    private String stateStr;
    /**
     * （用户考试）考试开始时间
     */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date beginTime;
    /**
     * （用户考试）考试完成时间
     */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date finishTime;
}
