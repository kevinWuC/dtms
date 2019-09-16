package com.medical.dtms.common.model.train;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;
import lombok.Data;

import java.util.Date;

/**
 * @version： QMSUserModel.java v 1.0, 2019年08月20日 18:10  Exp$
 * @Description 培训用户model
 **/
@Data
public class TrainUserQueryModel {
    /**
     * 业务主键
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long bizId;
    /**
     * 培训id
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long trainId;
    /**
     * 用户id
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long userId;
    /**
     * 文件阅读
     */
    private Boolean isFinishFile;
    /**
     * 阅读用时
     */
    private Date readTime;
    /**
     * 培训阶段 ，-1未开始0正进行1已完成
     */
    private Integer state;
    /**
     * 开始时间
     */
    private Date beginTime;
    /**
     * 答案
     */
    private String answer;
    /**
     * 内容
     */
    private String des;
    /**
     * 考试开始时间
     */
    private Date startDate;
    /**
     * 考试结束时间
     */
    private Date endDate;
    /**
     * 完成时间
     */
    private Date finishTime;
    /**
     * 是否完成试题
     */
    private Boolean isFinishQuestions;
    /**
     * 培训名称
     */
    private String trainName;
    /**
     * 培训类型id
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long typeId;
    /**
     * 及格分数
     */
    private Integer passPoint;
    /**
     * 总分
     */
    private Integer totalPoints;
    /**
     * 得分
     */
    private String pointStr;
    /**
     * 是否及格 true 是 false 否
     */
    private Boolean pass;
    /**
     * 开始阅读时间
     */
    private Date startReadTime;
    /**
     * 是否删除 0 否 1是
     */
    private Boolean isDeleted;
    /**
     * 文件id
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long fileId;
    /** 文件名称*/
    private String fileName;
    /**
     * 用户姓名
     */
    private String dspName;
    /**
     * 部门名称
     */
    private String deptName;
    /**
     * 参加培训人数
     */
    private Integer trainTotal;
    /**
     * 培训及时人数
     */
    private Integer timelyTotal;
}
