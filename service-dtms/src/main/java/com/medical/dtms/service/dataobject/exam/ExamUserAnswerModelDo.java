package com.medical.dtms.service.dataobject.exam;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;
import lombok.Data;

import javax.persistence.Table;
import java.util.Date;

/**
 * @version： ExamUserAnswerModelManager.java v 1.0, 2019年09月11日 21:19 huangshuaiquan Exp$
 * @Description
 **/
@Data
@Table(name = "tb_dtms_exam_user_answer_model")
public class ExamUserAnswerModelDo {

    /**
     * 自增主键
     **/
    private Long id;
    /**
     * 业务主键
     **/
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long examUserAnswerModeId;
    /**
     * 试题ID
     **/
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long questionsId;
    /**
     * 试卷ID
     **/
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long examId;
    /**
     * 试卷安排ID
     **/
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long examPlanId;
    /**
     * 考试ID
     **/
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long examUserPlanId;
    /**
     * 用户ID
     **/
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long userId;
    /**
     * 答案
     **/
    private String answer;
    /**
     * 本题得分
     **/
    private Integer answerPoints;
    /**
     * 排序
     **/
    private Integer sortCode;
    /**
     * 删除标记:true-删除，false-正常
     */
    private Boolean isDeleted;
    /**
     * 创建用户
     */
    private String creator;
    /**
     * 创建用户主键
     */
    private String creatorId;
    /**
     * 创建时间
     */
    private Date gmtCreated;
    /**
     * 修改用户
     */
    private String modifier;
    /**
     * 修改用户主键
     */
    private String modifierId;
    /**
     * 修改时间
     */
    private Date gmtModified;
}