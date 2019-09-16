/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.medical.dtms.dto.question;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;
import lombok.Data;

import java.util.List;

/**
 * 试题查询类
 * @author shenqifeng
 * @version $Id: QuestionQuery.java, v 0.1 2019年8月27日 下午9:12:44 shenqifeng Exp $
 */
@Data
public class QuestionQuery {
    /**培训类别*/
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long         examTypeId;
    /**试题库类别*/
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long         questionsBankTypeId;
    /**试题类别*/
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long         questionsTypeId;
    /**试题题目*/
    private String       questionTitle;
    /**已选题目*/
    private List<String> questionIds;
    private Integer      pageNo;
    private Integer      pageSize;
    /**
     * 培训类别 -examTypeId   试题库类别-questionsBankTypeId   试题类别  -questionsTypeId
     */
    private String       type;
    /**
     * 问题类型 id 集合
     * */
    private List<Long> questionsTypeIds;
}
