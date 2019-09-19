package com.medical.dtms.dto.exam;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;

import lombok.Data;

/**
 * 用户考试计划
 * 
 * @author shenqifeng
 * @version $Id: ExamUserPlanModelDTO.java, v 0.1 2019年9月10日 下午10:01:12 shenqifeng Exp $
 */
@Data
public class ExamUserPlanModelDTO {
    /**主键*/
    private Long    id;
    /**业务主键*/
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long    examUserPlanModelId;
    /**是否结束*/
    private Boolean isFinish;
    /**是否开始考试*/
    private Boolean isBegin;
    /**考试时间*/
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date    examTime;
    /**考试安排id*/
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long    examPlanId;
    private String  examPlanName;
    /**试卷id*/
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long    examId;
    /**是否开启*/
    private Boolean isStart;
    /**开始时间*/
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date    startDate;
    /**结束时间*/
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date    endDate;
    /**时长*/
    private Integer examDuration;
    /**总分*/
    private Integer totalPoints;
    /**通过分数*/
    private Integer passPoints;
    /**用户id*/
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long    userId;
    /**得分*/
    private Integer baseTotalPoints;
    /**是否批卷*/
    private Boolean isMark;
    /**是否删除*/
    private Boolean isDeleted;
    /***/
    private Date    createDate;
    /***/
    private Long    createUserId;
    /***/
    private String  createUserName;
    /***/
    private Date    modifyDate;
    /***/
    private Long    modifyUserId;
    /***/
    private String  modifyUserName;

}