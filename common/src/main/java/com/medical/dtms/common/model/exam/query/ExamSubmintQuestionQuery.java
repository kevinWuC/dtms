package com.medical.dtms.common.model.exam.query;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;
import lombok.Data;

/**
 * @version： ExamSubmintQuestionQuery.java v 1.0, 2019年09月11日 22:46 huangshuaiquan Exp$
 * @Description
 **/
@Data
public class ExamSubmintQuestionQuery {

    /**
     * 业务主键
     **/
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long bizId;
    /**
     * 试题ID
     **/
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long questionsId;
    /**
     * 本题可得分
     **/
    private Integer questionsPoints;
    /**
     * 答案
     **/
    private String answer;

}
