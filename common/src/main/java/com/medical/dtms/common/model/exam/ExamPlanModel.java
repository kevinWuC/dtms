/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.medical.dtms.common.model.exam;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;

import lombok.Data;

/**
 * 试卷for plan
 * @author shenqifeng
 * @version $Id: ExamPlanModel.java, v 0.1 2019年9月8日 上午8:41:48 shenqifeng Exp $
 */
@Data
public class ExamPlanModel {
    /**试卷id*/
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long    examId;
    /**试卷名称*/
    private String  examName;
    private Integer totalPoints;

}
