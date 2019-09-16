package com.medical.dtms.common.model.question;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;

import lombok.Data;

/**
 * 列表试题model
 * 
 * @author shenqifeng
 * @version $Id: DtmsQuestionListModel.java, v 0.1 2019年8月27日 下午5:18:16 shenqifeng Exp $
 */
@Data
public class DtmsQuestionListModel {

    /**试题id*/
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long    bizId;
    /**试题类别*/
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long    questionsTypeId;
    private String  questionsTypeName;
    /**试题题目*/
    private String  questionTitle;
    /**修改日期*/
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date    modifyDate;
    /**修改者*/
    private String  modifyUserName;
    /**是否使用*/
    private Integer isUsed;

}