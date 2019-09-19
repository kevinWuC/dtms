package com.medical.dtms.service.dataobject.exam;

import java.util.Date;

import lombok.Data;

/**
 * 用户考试计划
 * 
 * @author shenqifeng
 * @version $Id: ExamUserPlanModelDO.java, v 0.1 2019年9月10日 下午10:01:12 shenqifeng Exp $
 */
@Data
public class ExamUserPlanModelDO {
    /**主键*/
    private Long    id;
    /**业务主键*/
    private Long    examUserPlanModelId;
    /**是否结束*/
    private Boolean isFinish;
    /**是否开始考试*/
    private Boolean isBegin;
    /**考试时间*/
    private Date    examTime;
    /**考试安排id*/
    private Long    examPlanId;
    /**试卷id*/
    private Long    examId;
    /**是否开启*/
    private Boolean isStart;
    /**开始时间*/
    private Date    startDate;
    /**结束时间*/
    private Date    endDate;
    /**时长*/
    private Integer examDuration;
    /**总分*/
    private Integer totalPoints;
    /**通过分数*/
    private Integer passPoints;
    /**用户id*/
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