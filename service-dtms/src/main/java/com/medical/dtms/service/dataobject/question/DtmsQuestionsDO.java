package com.medical.dtms.service.dataobject.question;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;

import com.medical.dtms.logclient.annotation.LogTag;
import com.medical.dtms.logclient.handler.BuiltinTypeHandler;
import lombok.Data;

import javax.persistence.Table;

/**
 * 试卷管理
 * 
 * @author shenqifeng
 * @version $Id: DtmsQuestionsDO.java, v 0.1 2019年8月27日 下午5:18:16 shenqifeng Exp $
 */
@Data
@Table(name = "tb_dtms_questions")
public class DtmsQuestionsDO {
    /**自增主键*/
    private Long    id;
    /**试题id*/
    @LogTag(alias = "bizId", builtinType = BuiltinTypeHandler.NORMAL)
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long    bizId;
    /**培训类别*/
    @LogTag(alias = "examTypeId", builtinType = BuiltinTypeHandler.NORMAL)
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long    examTypeId;
    /**试题库类别*/
    @LogTag(alias = "questionsBankTypeId", builtinType = BuiltinTypeHandler.NORMAL)
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long    questionsBankTypeId;
    /**试题类别*/
    @LogTag(alias = "questionsTypeId", builtinType = BuiltinTypeHandler.NORMAL)
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long    questionsTypeId;
    /**试题题目*/
    @LogTag(alias = "questionTitle", builtinType = BuiltinTypeHandler.NORMAL)
    private String  questionTitle;
    /**试题内容*/
    @LogTag(alias = "questionContent", builtinType = BuiltinTypeHandler.NORMAL)
    private String  questionContent;
    /**A答案*/
    @LogTag(alias = "questionA", builtinType = BuiltinTypeHandler.NORMAL)
    private String  questionA;
    /**B答案*/
    @LogTag(alias = "questionB", builtinType = BuiltinTypeHandler.NORMAL)
    private String  questionB;
    /**C答案*/
    @LogTag(alias = "questionC", builtinType = BuiltinTypeHandler.NORMAL)
    private String  questionC;
    /**D答案*/
    @LogTag(alias = "questionD", builtinType = BuiltinTypeHandler.NORMAL)
    private String  questionD;
    /**E答案*/
    @LogTag(alias = "questionE", builtinType = BuiltinTypeHandler.NORMAL)
    private String  questionE;
    /**F答案*/
    @LogTag(alias = "questionF", builtinType = BuiltinTypeHandler.NORMAL)
    private String  questionF;
    /**答案*/
    @LogTag(alias = "answer", builtinType = BuiltinTypeHandler.NORMAL)
    private String  answer;
    /**使用部门ID*/
    @LogTag(alias = "useDeptId", builtinType = BuiltinTypeHandler.NORMAL)
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
    @LogTag(alias = "deleted", builtinType = BuiltinTypeHandler.NORMAL)
    private Boolean deleted;
    /**是否使用*/
    @LogTag(alias = "isUsed", builtinType = BuiltinTypeHandler.NORMAL)
    private Boolean isUsed;
    /**附件*/
    @LogTag(alias = "imgs", builtinType = BuiltinTypeHandler.NORMAL)
    private String  imgs;
    /**源地址*/
    @LogTag(alias = "sourceUrl", builtinType = BuiltinTypeHandler.NORMAL)
    private String  sourceUrl;
    /***/
    @LogTag(alias = "examExplain", builtinType = BuiltinTypeHandler.NORMAL)
    private String  examExplain;

}