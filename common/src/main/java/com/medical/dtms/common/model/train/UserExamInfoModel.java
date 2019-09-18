package com.medical.dtms.common.model.train;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;
import lombok.Data;

/**
 * @version: UserExamInfoModel$.java v 1.0,2019$年09$月17$日19:21$ ruanqiuhan Exp$
 * @Descrption 用户考试情况
 **/
@Data
public class UserExamInfoModel {
    /**
     * 试卷id
     */
    private Long examId;
    /**
     * 试题类型
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long questionsTypeId;
    private String questionTypeName;
    /**
     * 分值
     */
    private Integer questionsPoints;
    /**
     * 试题题目
     */
    private String questionTitle;
    /**
     * 试题内容
     */
    private String questionContent;
    /**
     * A答案
     */
    private String questionA;
    /**
     * B答案
     */
    private String questionB;
    /**
     * C答案
     */
    private String questionC;
    /**
     * D答案
     */
    private String questionD;
    /**
     * E答案
     */
    private String questionE;
    /**
     * F答案
     */
    private String questionF;
    /**
     * 答案
     */
    private String answer;
    /**
     * 用户答案
     */
    private String userAnswer;
    /**
     * 本题得分
     */
    private String answerPoints;
}
