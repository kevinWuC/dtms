package com.medical.dtms.common.model.train;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;
import com.medical.dtms.common.model.exam.ExamQuestionsTypeModel;
import lombok.Data;

import java.util.List;

/**
 * @version: MyTrainTestModel$.java v 1.0,2019$年09$月17$日18:24$ ruanqiuhan Exp$
 * @Descrption 我的培训 考试
 **/
@Data
public class MyTrainTestModel {

    /**
     * 业务主键
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long bizId;
    /**
     * 考试人员
     */
    private String userName;
    /**
     * 试卷id
     */
    private Long examId;
    /**
     * 试卷名称
     */
    private String examName;
    /**
     * 考试时间
     */
    private Integer examDuration;
    /**
     * 考试总分
     */
    private Integer totalPoints;
    /**
     * 合格分数
     */
    private Integer passPoint;
    /**
     * 我的得分
     */
    private String pointStr;
    /**
     * 试卷集合
     */
    private  List<UserExamInfoModel> models;

}
