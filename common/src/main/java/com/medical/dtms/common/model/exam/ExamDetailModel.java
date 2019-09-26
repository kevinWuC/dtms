/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.medical.dtms.common.model.exam;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;

import lombok.Data;

/**
 * 试卷详情
 * @author shenqifeng
 * @version $Id: ExamDetalModel.java, v 0.1 2019年9月8日 上午8:41:48 shenqifeng Exp $
 */
@Data
public class ExamDetailModel {
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
    /**是否使用中*/
    private Boolean isUse;
    /**是否培训*/
    private Boolean isPeixun;

    private List<ExamQuestionsTypeModel> examQuestionTypes;
    /***/
    private Long    modifyUserId;
    /***/
    private String  modifyUserName;
}
