package com.medical.dtms.service.dataobject.exam;

import java.util.Date;

import lombok.Data;

import javax.persistence.Table;

/***
 * 试卷信息
 * 
 * @author shenqifeng
 * @version $Id: ExamModelDO.java, v 0.1 2019年9月7日 下午8:08:36 shenqifeng Exp $
 */
@Data
@Table(name = "tb_dtms_exam_model")
public class ExamModelDO {
    /**主键*/
    private Long    id;
    /**试卷id*/
    private Long    examId;
    /**试卷名称*/
    private String  examName;
    /**考试时长*/
    private Integer examDuration;
    /**总分*/
    private Integer totalPoints;
    /**是否使用中*/
    private Boolean isUse;
    /**是否培训*/
    private Boolean isPeixun;
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