package com.medical.dtms.dto.exam;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;

import lombok.Data;

/**
 * 试卷关联试题类型表
 * 
 * @author shenqifeng
 * @version $Id: ExamQuestionSTypeDTO.java, v 0.1 2019年9月7日 下午8:12:25 shenqifeng Exp $
 */
@Data
public class ExamQuestionsTypeDTO {
    /**主键*/
    private Long       id;
    /**业务主键*/
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long       examQuestionTypeId;
    /**试卷id*/
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long       examId;
    /**试题类型*/
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long       questionsTypeId;
    /**分值*/
    private Integer    questionsPoints;
    /**总分*/
    private Integer    totalPoints;
    /**数量*/
    private Integer    questionSum;
    /***/
    private Date       createDate;
    /***/
    private Long       createUserId;
    /***/
    private String     createUserName;
    private List<Long> examQuestions;

}