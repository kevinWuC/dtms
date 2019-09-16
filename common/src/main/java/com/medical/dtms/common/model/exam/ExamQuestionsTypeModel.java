package com.medical.dtms.common.model.exam;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;
import com.medical.dtms.common.model.question.DtmsQuestionListModel;
import com.medical.dtms.common.model.question.DtmsQuestionsModel;

import lombok.Data;

/**
 * 试卷关联试题类型表
 * 
 * @author shenqifeng
 * @version $Id: ExamQuestionsTypeModel.java, v 0.1 2019年9月7日 下午8:12:25 shenqifeng Exp $
 */
@Data
public class ExamQuestionsTypeModel {

    /**业务主键*/
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long                        examQuestionTypeId;
    /**试卷id*/
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long                        examId;
    /**试题类型*/
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long                        questionsTypeId;
    private String                      questionTypeName;
    /**分值*/
    private Integer                     questionsPoints;
    /**总分*/
    private Integer                     totalPoints;
    /**数量*/
    private Integer                     questionSum;
    private String                      examQuestionString;
    private List<DtmsQuestionListModel> examQuestions;
    /**
     * 预览题目
     */
    private List<DtmsQuestionsModel>    questionForPreview;

}