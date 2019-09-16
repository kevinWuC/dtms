package com.medical.dtms.dto.exam;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;

import lombok.Data;

/***
 * 试卷信息
 * 
 * @author shenqifeng
 * @version $Id: ExamModelDTO.java, v 0.1 2019年9月7日 下午8:08:36 shenqifeng Exp $
 */
@Data
public class ExamModelDTO {
    /**主键*/
    private Long    id;
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
    /***/
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date    createDate;
    /***/
    private Long    createUserId;
    /***/
    private String  createUserName;
    /***/
    private Date    modifyDate;
    /***/
    private Long    modifyUserId;
    /***/
    private String  modifyUserName;

    private List<ExamQuestionsTypeDTO> examQuestionsTypes;

}