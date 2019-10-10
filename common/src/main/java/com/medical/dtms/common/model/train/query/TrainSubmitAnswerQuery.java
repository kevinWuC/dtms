package com.medical.dtms.common.model.train.query;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;
import com.medical.dtms.common.model.exam.query.ExamSubmintQuestionQuery;
import lombok.Data;

import java.util.List;

/**
 * @version： ExamSubmitAnswerQuery.java v 1.0, 2019年09月11日 19:10 huangshuaiquan Exp$
 * @Description
 **/
@Data
public class TrainSubmitAnswerQuery {


    /**
     * 培训id
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long trainId;
    /**
     * 试卷ID
     **/
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long examId;
    /**
     * 培训用户ID
     **/
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long trainUserId;
    /**
     * 用户ID
     **/
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long userId;
    /**
     * 修改用户
     */
    private String modifier;
    /**
     * 修改用户主键
     */
    private String modifierId;
    /**
     * 试题答案集合
     **/
    private List<ExamSubmintQuestionQuery> questions;




}
