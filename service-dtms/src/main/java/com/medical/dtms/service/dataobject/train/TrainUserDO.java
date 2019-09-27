package com.medical.dtms.service.dataobject.train;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;
import com.medical.dtms.logclient.annotation.LogTag;
import com.medical.dtms.logclient.handler.BuiltinTypeHandler;
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
    @LogTag(alias = "bizId", builtinType = BuiltinTypeHandler.NORMAL)
    private Long bizId;
    /**
     * 培训id
     */
    @LogTag(alias = "trainId", builtinType = BuiltinTypeHandler.NORMAL)
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long trainId;
    /**
     * 用户id
     */
    @LogTag(alias = "userId", builtinType = BuiltinTypeHandler.NORMAL)
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long userId;
    /**
     * 用户id 集合
     */
    @LogTag(alias = "userIdList", builtinType = BuiltinTypeHandler.NORMAL)
    private List<Long> userIdList;
    /**
     * 文件阅读
     */
    @LogTag(alias = "isFinishFile", builtinType = BuiltinTypeHandler.NORMAL)
    private Boolean isFinishFile;
    /**
     * 阅读用时
     */
    @LogTag(alias = "readTime", builtinType = BuiltinTypeHandler.NORMAL)
    private Date readTime;
    /**
     * 培训阶段 ，-1未开始0正进行1已完成
     */
    @LogTag(alias = "state", builtinType = BuiltinTypeHandler.NORMAL)
    private Integer state;
    /**
     * 开始时间
     */
    @LogTag(alias = "beginTime", builtinType = BuiltinTypeHandler.NORMAL)
    private Date beginTime;
    /**
     * 答案
     */
    @LogTag(alias = "answer", builtinType = BuiltinTypeHandler.NORMAL)
    private String answer;
    /**
     * 内容
     */
    @LogTag(alias = "des", builtinType = BuiltinTypeHandler.NORMAL)
    private String des;
    /**
     * 考试开始时间
     */
    @LogTag(alias = "startDate", builtinType = BuiltinTypeHandler.NORMAL)
    private Date startDate;
    /**
     * 考试结束时间
     */
    @LogTag(alias = "endDate", builtinType = BuiltinTypeHandler.NORMAL)
    private Date endDate;
    /**
     * 完成时间
     */
    @LogTag(alias = "finishTime", builtinType = BuiltinTypeHandler.NORMAL)
    private Date finishTime;
    /**
     * 是否完成试题
     */
    @LogTag(alias = "isFinishQuestions", builtinType = BuiltinTypeHandler.NORMAL)
    private Boolean isFinishQuestions;
    /**
     * 培训名称
     */
    @LogTag(alias = "trainName", builtinType = BuiltinTypeHandler.NORMAL)
    private String trainName;
    /**
     * 参加培训人数
     */
    @LogTag(alias = "trainTotal", builtinType = BuiltinTypeHandler.NORMAL)
    private Integer trainTotal;
    /**
     * 培训及时人数
     */
    @LogTag(alias = "timelyTotal", builtinType = BuiltinTypeHandler.NORMAL)
    private Integer timelyTotal;
    /**
     * 培训及时率
     */
    @LogTag(alias = "timelinessRate", builtinType = BuiltinTypeHandler.NORMAL)
    private Integer timelinessRate;
    /**
     * 培训类型id
     */
    @LogTag(alias = "typeId", builtinType = BuiltinTypeHandler.NORMAL)
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long typeId;
    /**
     * 培训考试分数
     */
    @LogTag(alias = "userExamTotal", builtinType = BuiltinTypeHandler.NORMAL)
    private Integer userExamTotal;
    /**
     * 得分
     */
    @LogTag(alias = "pointStr", builtinType = BuiltinTypeHandler.NORMAL)
    private String pointStr;
    /**
     * 开始阅读时间
     */
    @LogTag(alias = "startReadTime", builtinType = BuiltinTypeHandler.NORMAL)
    private Date startReadTime;
    /**
     * 是否删除 0 否 1是
     */
    @LogTag(alias = "isDeleted", builtinType = BuiltinTypeHandler.NORMAL)
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