package com.medical.dtms.service.dataobject.exam;

import java.util.Date;

import com.medical.dtms.logclient.annotation.LogTag;
import com.medical.dtms.logclient.handler.BuiltinTypeHandler;
import lombok.Data;

import javax.persistence.Table;

/**
 * 考试安排
 * 
 * @author shenqifeng
 * @version $Id: ExamPlanModelDO.java, v 0.1 2019年9月9日 下午9:41:51 shenqifeng Exp $
 */
@Data
@Table(name = "tb_dtms_exam_plan_model")
public class ExamPlanModelDO {
    /**主键*/
    private Long    id;
    /**考试安排id*/
    @LogTag(alias = "examPlanModelId", builtinType = BuiltinTypeHandler.NORMAL)
    private Long    examPlanModelId;
    /**考试名称*/
    @LogTag(alias = "examPlanName", builtinType = BuiltinTypeHandler.NORMAL)
    private String  examPlanName;
    /**使用部门*/
    @LogTag(alias = "useDeptId", builtinType = BuiltinTypeHandler.NORMAL)
    private Long    useDeptId;
    /**试卷ID*/
    @LogTag(alias = "examId", builtinType = BuiltinTypeHandler.NORMAL)
    private Long    examId;
    /**考试说明*/
    @LogTag(alias = "examInfo", builtinType = BuiltinTypeHandler.NORMAL)
    private String  examInfo;
    /**考试内容*/
    @LogTag(alias = "examContent", builtinType = BuiltinTypeHandler.NORMAL)
    private String  examContent;
    /**是否开始*/
    @LogTag(alias = "isStart", builtinType = BuiltinTypeHandler.NORMAL)
    private Boolean isStart;
    /**开始日期*/
    @LogTag(alias = "startDate", builtinType = BuiltinTypeHandler.NORMAL)
    private Date    startDate;
    /**结束日期*/
    @LogTag(alias = "endDate", builtinType = BuiltinTypeHandler.NORMAL)
    private Date    endDate;
    /**考试时长*/
    @LogTag(alias = "examDuration", builtinType = BuiltinTypeHandler.NORMAL)
    private Integer examDuration;
    /**总分*/
    @LogTag(alias = "totalPoints", builtinType = BuiltinTypeHandler.NORMAL)
    private Integer totalPoints;
    /**合格分数*/
    @LogTag(alias = "passPoints", builtinType = BuiltinTypeHandler.NORMAL)
    private Integer passPoints;
    /**单选题分值*/
    @LogTag(alias = "typePoints1", builtinType = BuiltinTypeHandler.NORMAL)
    private Integer typePoints1;
    /**多选题分值*/
    @LogTag(alias = "typePoints2", builtinType = BuiltinTypeHandler.NORMAL)
    private Integer typePoints2;
    /**判断题分值*/
    @LogTag(alias = "typePoints3", builtinType = BuiltinTypeHandler.NORMAL)
    private Integer typePoints3;
    /**问答题分值*/
    @LogTag(alias = "typePoints4", builtinType = BuiltinTypeHandler.NORMAL)
    private Integer typePoints4;
    /**简述题分值*/
    @LogTag(alias = "typePoints5", builtinType = BuiltinTypeHandler.NORMAL)
    private Integer typePoints5;
    /**创建日期*/
    private Date    createdate;
    /**创建者ID*/
    private Long    createUserId;
    /**创建者*/
    private String  createUserName;
    /**修改日期*/
    private Date    modifyDate;
    /**修改人id*/
    private Long    modifyUserId;
    /**修改者*/
    private String  modifyUserName;
    /**排序*/
    @LogTag(alias = "sortCode", builtinType = BuiltinTypeHandler.NORMAL)
    private Integer sortCode;
    @LogTag(alias = "deleted", builtinType = BuiltinTypeHandler.NORMAL)
    private Boolean deleted;

}