package com.medical.dtms.dto.exam;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;

import lombok.Data;

/**
 * 考试安排
 * 
 * @author shenqifeng
 * @version $Id: ExamPlanModelDTO.java, v 0.1 2019年9月9日 下午9:41:51 shenqifeng Exp $
 */
@Data
public class ExamPlanModelDTO {
    /**主键*/
    private Long       id;
    /**考试安排id*/
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long       examPlanModelId;
    /**考试名称*/
    private String     examPlanName;
    /**使用部门*/
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long       useDeptId;
    private String     deptName;
    /**试卷ID*/
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long       examId;
    /**考试说明*/
    private String     examInfo;
    /**考试内容*/
    private String     examContent;
    /**是否开始*/
    private Boolean    isStart;
    /**开始日期*/
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date       startDate;
    /**结束日期*/
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date       endDate;
    /**考试时长*/
    private Integer    examDuration;
    /**总分*/
    private Integer    totalPoints;
    /**合格分数*/
    private Integer    passPoints;
    /**单选题分值*/
    private Integer    typePoints1;
    /**多选题分值*/
    private Integer    typePoints2;
    /**判断题分值*/
    private Integer    typePoints3;
    /**问答题分值*/
    private Integer    typePoints4;
    /**简述题分值*/
    private Integer    typePoints5;
    /**创建日期*/
    private Date       createdate;
    /**创建者ID*/
    private Long       createUserId;
    /**创建者*/
    private String     createUserName;
    /**修改日期*/
    private Date       modifyDate;
    /**修改人id*/
    private Long       modifyUserId;
    /**修改者*/
    private String     modifyUserName;
    /**排序*/
    private Integer    sortCode;
    private Boolean    deleted;
    /**人员配置选择的人*/
    private List<Long> userIds;

}