package com.medical.dtms.service.dataobject.exam;

import java.util.Date;

import lombok.Data;

/**
 * 试卷关联试题类型表
 * 
 * @author shenqifeng
 * @version $Id: ExamQuestionSTypeDO.java, v 0.1 2019年9月7日 下午8:12:25 shenqifeng Exp $
 */
@Data
public class ExamQuestionsTypeDO {
    /**主键*/
    private Long    id;
    /**业务主键*/
    private Long    examQuestionTypeId;
    /**试卷id*/
    private Long    examId;
    /**试题类型*/
    private Long    questionsTypeId;
    /**分值*/
    private Integer questionsPoints;
    /**总分*/
    private Integer totalPoints;
    /**数量*/
    private Integer questionSum;
    /***/
    private Date    createDate;
    /***/
    private Long    createUserId;
    /***/
    private String  createUserName;

}