package com.medical.dtms.service.dataobject.train;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;
import lombok.Data;

import javax.persistence.Table;
import java.util.Date;
import java.util.List;

/**
 * @version： TrainUserDO.java v 1.0, 2019年08月14日 10:52  Exp$
 * @Description 培训用户表
 **/
@Data
@Table(name = "tb_dtms_train_user")
public class TrainUserDO {
    /**
     * 自增主键
     */
    private Long id;
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
     * 用户id 集合
     */
    private List<Long> userIdList;
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
     * 参加培训人数
     */
    private Integer trainTotal;
    /**
     * 培训及时人数
     */
    private Integer timelyTotal;
    /**
     * 培训及时率
     */
    private Integer timelinessRate;
    /**
     * 培训类型id
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long typeId;
    /**
     * 培训考试分数
     */
    private Integer userExamTotal;
    /**
     * 得分
     */
    private String pointStr;
    /**
     * 开始阅读时间
     */
    private Date startReadTime;
    /**
     * 是否删除 0 否 1是
     */
    private Boolean isDeleted;
    /**
     * 创建者
     */
    private String creator;
    /**
     * 创建者id
     */
    private String creatorId;
    /**
     * 创建日期
     */
    private Date gmtCreated;
    /**
     * 修改者
     */
    private String modifier;
    /**
     * 修改者Id
     */
    private String modifierId;
    /**
     * 修改日期
     */
    private Date gmtModified;
}