package com.medical.dtms.service.dataobject.exam;

import java.util.Date;

import lombok.Data;

/**
 * 试卷与试题关联表
 * 
 * @author shenqifeng
 * @version $Id: ExamQuestionsDO.java, v 0.1 2019年9月7日 下午8:10:47 shenqifeng Exp $
 */
@Data
public class ExamQuestionsDO {
    /**主键*/
    private Long   id;
    /**业务主键*/
    private Long   examQuestionId;
    /**试卷id*/
    private Long   examId;
    /**试卷试题类型id*/
    private Long   examQuestionTypeId;
    /**试题id*/
    private Long   questionsId;
    /***/
    private Date   createDate;
    /***/
    private Long   createUserId;
    /***/
    private String createUserName;

}