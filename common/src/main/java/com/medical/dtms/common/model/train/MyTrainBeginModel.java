package com.medical.dtms.common.model.train;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;
import com.medical.dtms.common.model.exam.ExamQuestionsTypeModel;
import lombok.Data;

import java.util.List;

/**
 * @version： MyTrainPageModel.java v 1.0, 2019年10月11日 12:05 wuxuelin Exp$
 * @Description 我的培训 - 开始考试model
 **/
@Data
public class MyTrainBeginModel {

    /**
     * 用户姓名
     */
    private String dspName;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 试卷名称
     */
    private String examName;
    /**
     * 考试时长
     */
    private Integer examDuration;
    /**
     * 总分
     */
    private Integer totalPoints;
    /**
     * 及格分数
     */
    private Integer passPoint;
    /**
     * 试卷id
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long examId;
    private List<ExamQuestionsTypeModel> examQuestionTypes;
}
