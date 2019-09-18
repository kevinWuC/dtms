package com.medical.dtms.common.model.exam;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;
import lombok.Data;

import java.util.List;

/**
 * @version： ExamStartModel.java v 1.0, 2019年09月11日 18:01 huangshuaiquan Exp$
 * @Description
 **/
@Data
public class ExamStartModel {

    /**
     * 用户姓名
     */
    private String dspName;
    /**用户id*/
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long    userId;
    /**参与本次考试安排的用户关联id*/
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long    examUserPlanModelId;
    /**考试安排id*/
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long    examPlanId;
    private String  examPlanName;
    /**试卷id*/
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long    examId;
    /**试卷名称*/
    private String  examName;
    /**考试时长*/
    private Integer examDuration;
    /**总分*/
    private Integer totalPoints;
    /**通过分数*/
    private Integer passPoints;
    /**用户得分*/
    private Integer userPoints;

    private List<ExamQuestionsTypeModel> examQuestionTypes;

}
