/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.medical.dtms.dto.exam.query;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;

import lombok.Data;

/**
 * 
 * @author shenqifeng
 * @version $Id: ExamQuery.java, v 0.1 2019年9月7日 下午10:11:38 shenqifeng Exp $
 */
@Data
public class ExamQuery {
    private String  examName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date    startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date    endTime;
    private Integer pageNo;
    private Integer pageSize;
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long    examId;
}
