package com.medical.dtms.common.model.train;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;
import com.medical.dtms.common.model.exam.query.ExamSubmintQuestionQuery;
import lombok.Data;

import java.util.List;

/**
 * @version： MyTrainSubmitModel.java v 1.0, 2019年08月29日 12:45 ruanqiuhan Exp$
 * @Description 用户培训 - 提交试卷 model
 **/
@Data
public class MyTrainSubmitModel {

    /**
     * 试卷ID
     **/
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long examId;
    /**
     * 培训ID
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long trainId;
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
