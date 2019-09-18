/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.medical.dtms.dto.exam.query;

import lombok.Data;

/**
 * 
 * @author shenqifeng
 * @version $Id: ExamPlanModelQuery.java, v 0.1 2019年9月7日 下午10:11:38 shenqifeng Exp $
 */
@Data
public class ExamPlanModelQuery {
    private String  examPlanName;
    private Integer pageNo;
    private Integer pageSize;
    private Long    userId;
    private Boolean isMark;
}
