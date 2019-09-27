package com.medical.dtms.service.dataobject.exam;

import java.util.Date;

import com.medical.dtms.logclient.annotation.LogTag;
import com.medical.dtms.logclient.handler.BuiltinTypeHandler;
import lombok.Data;

import javax.persistence.Table;

/**
 * 用户考试计划
 * 
 * @author shenqifeng
 * @version $Id: ExamUserPlanModelDO.java, v 0.1 2019年9月10日 下午10:01:12 shenqifeng Exp $
 */
@Data
@Table(name = "tb_dtms_exam_user_plan_model")
public class ExamUserPlanModelDO {
    /**主键*/
    private Long    id;
    /**业务主键*/
    @LogTag(alias = "examUserPlanModelId", builtinType = BuiltinTypeHandler.NORMAL)
    private Long    examUserPlanModelId;
    /**是否结束*/
    @LogTag(alias = "isFinish", builtinType = BuiltinTypeHandler.NORMAL)
    private Boolean isFinish;
    /**是否开始考试*/
    @LogTag(alias = "isBegin", builtinType = BuiltinTypeHandler.NORMAL)
    private Boolean isBegin;
    /**考试时间*/
    @LogTag(alias = "examTime", builtinType = BuiltinTypeHandler.NORMAL)
    private Date    examTime;
    /**考试安排id*/
    @LogTag(alias = "examPlanId", builtinType = BuiltinTypeHandler.NORMAL)
    private Long    examPlanId;
    /**试卷id*/
    @LogTag(alias = "examId", builtinType = BuiltinTypeHandler.NORMAL)
    private Long    examId;
    /**是否开启*/
    @LogTag(alias = "isStart", builtinType = BuiltinTypeHandler.NORMAL)
    private Boolean isStart;
    /**开始时间*/
    @LogTag(alias = "startDate", builtinType = BuiltinTypeHandler.NORMAL)
    private Date    startDate;
    /**结束时间*/
    @LogTag(alias = "endDate", builtinType = BuiltinTypeHandler.NORMAL)
    private Date    endDate;
    /**时长*/
    @LogTag(alias = "examDuration", builtinType = BuiltinTypeHandler.NORMAL)
    private Integer examDuration;
    /**总分*/
    @LogTag(alias = "totalPoints", builtinType = BuiltinTypeHandler.NORMAL)
    private Integer totalPoints;
    /**通过分数*/
    @LogTag(alias = "passPoints", builtinType = BuiltinTypeHandler.NORMAL)
    private Integer passPoints;
    /**用户id*/
    @LogTag(alias = "userId", builtinType = BuiltinTypeHandler.NORMAL)
    private Long    userId;
    /**得分*/
    @LogTag(alias = "baseTotalPoints", builtinType = BuiltinTypeHandler.NORMAL)
    private Integer baseTotalPoints;
    /**是否批卷*/
    @LogTag(alias = "isMark", builtinType = BuiltinTypeHandler.NORMAL)
    private Boolean isMark;
    /**是否删除*/
    @LogTag(alias = "isDeleted", builtinType = BuiltinTypeHandler.NORMAL)
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