package com.medical.dtms.dto.question;

import java.util.Date;

import lombok.Data;

/**
 * 试卷管理
 * 
 * @author shenqifeng
 * @version $Id: DtmsQuestionsDTO.java, v 0.1 2019年8月27日 下午5:18:16 shenqifeng Exp $
 */
@Data
public class DtmsQuestionsDTO {
    /**自增主键*/
    private Long    id;
    /**试题id*/
    private Long    bizId;
    /**培训类别*/
    private Long    examTypeId;
    /**试题库类别*/
    private Long    questionsBankTypeId;
    /**试题类别*/
    private Long    questionsTypeId;
    /**试题题目*/
    private String  questionTitle;
    /**试题内容*/
    private String  questionContent;
    /**A答案*/
    private String  questionA;
    /**B答案*/
    private String  questionB;
    /**C答案*/
    private String  questionC;
    /**D答案*/
    private String  questionD;
    /**E答案*/
    private String  questionE;
    /**F答案*/
    private String  questionF;
    /**答案*/
    private String  answer;
    /**使用部门ID*/
    private String  useDeptId;
    /**创建日期*/
    private Date    createDate;
    /**创建者ID*/
    private Long    createUserId;
    /**创建者*/
    private String  createUserName;
    /**修改日期*/
    private Date    modifyDate;
    /**修改者ID*/
    private Long    modifyUserId;
    /**修改者*/
    private String  modifyUserName;
    private Boolean deleted;
    /**是否使用*/
    private Boolean isUsed;
    /**附件*/
    private String  imgs;
    /**源地址*/
    private String  sourceUrl;
    /***/
    private String  examExplain;

}