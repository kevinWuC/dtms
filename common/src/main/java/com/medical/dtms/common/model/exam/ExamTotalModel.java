package com.medical.dtms.common.model.exam;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;
import lombok.Data;

import java.util.Date;

/**
 * @version: TrainExcelModel$.java v 1.0,2019$年09$月09$日15:00ruanqiuhan Exp$
 * @Descrption 考试统计 - 查询查看
 **/
@Data
public class ExamTotalModel {

    /**
     * 用户姓名
     */
    private String dspName;
    /**
     * 用户id
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long userId;
    /**
     * 部门名称
     */
    private String deptName;
    /**
     * 考试名称
     */
    private String examName;
    /**
     * 得分
     */
    private String pointStr;
    /**
     * 及格分数
     */
    private Integer passPoint;
    /**
     * 是否及格 true 是 false 否
     */
    private Boolean pass;
    /**
     * 总分
     */
    private Integer totalPoints;
    /**
     * 及格/总分
     */
    private String passTotal;
    /**
     * 完成时间
     */
    private Date finishTime;
    /**
     * 完成时间 str
     */
    private String finishTimeStr;
    /**
     * 合格 不合格
     */
    private String passStr;
    /**
     * 培训id
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long trainId;
}
