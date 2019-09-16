/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.medical.dtms.common.model.question;

import com.medical.dtms.common.config.ExcelField;

import lombok.Data;

/**
 * 
 * @author shenqifeng
 * @version $Id: QuestionExcelModel.java, v 0.1 2019年8月31日 下午3:21:40 shenqifeng Exp $
 */
@Data
public class QuestionExcelModel {
    /**培训类别*/
    @ExcelField(name = "培训类别")
    private Long   examTypeId;
    /**试题库类别*/
    @ExcelField(name = "试题库类别")
    private Long   questionsBankTypeId;
    /**试题类别*/
    @ExcelField(name = "试题类别")
    private Long   questionsTypeId;
    /**试题题目*/
    @ExcelField(name = "试题题目")
    private String questionTitle;
    /**试题内容*/
    @ExcelField(name = "试题内容")
    private String questionContent;
    /**A答案*/
    @ExcelField(name = "A答案")
    private String questionA;
    /**B答案*/
    @ExcelField(name = "B答案")
    private String questionB;
    /**C答案*/
    @ExcelField(name = "C答案")
    private String questionC;
    /**D答案*/
    @ExcelField(name = "D答案")
    private String questionD;
    /**E答案*/
    @ExcelField(name = "E答案")
    private String questionE;
    /**F答案*/
    @ExcelField(name = "F答案")
    private String questionF;
    /**答案*/
    @ExcelField(name = "答案")
    private String answer;

}
