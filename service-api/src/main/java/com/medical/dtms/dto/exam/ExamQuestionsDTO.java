package com.medical.dtms.dto.exam;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;

import lombok.Data;

/**
 * 试卷与试题关联表
 * 
 * @author shenqifeng
 * @version $Id: ExamQuestionsDTO.java, v 0.1 2019年9月7日 下午8:10:47 shenqifeng Exp $
 */
@Data
public class ExamQuestionsDTO {
    /**主键*/
    private Long   id;
    /**业务主键*/
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long   examQuestionId;
    /**试卷id*/
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long   examId;
    /**试卷试题类型id*/
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long   examQuestionTypeId;
    /**试题id*/
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long   questionsId;
    /***/
    private Date   createDate;
    /***/
    private Long   createUserId;
    /***/
    private String createUserName;

}