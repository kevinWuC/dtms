package com.medical.dtms.service.dataobject.train;

import lombok.Data;

import java.util.Date;

/**
 * @version: TrainQuestionProcessDO$.java v 1.0,2019$年09$月10$日14:56$ ruanqiuhan Exp$
 * @Descrption 用户培训答题过程
 **/
@Data
public class TrainQuestionProcessDO {

    /**
     * 自增主键
     */
    private Long id;
    /**
     * 业务主键
     */
    private Long bizId;
    /**
     * 培训ID
     */
    private Long trainId;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 试题ID
     */
    private Long questionsId;
    /**
     * 试题类型ID
     */
    private Long questionTypeId;
    /**
     * 用户答案
     */
    private String userAnswer;
    /**
     * 是否正确 true 是 false 否
     */
    private Boolean isRight;
    /**
     * 创建日期
     */
    private Date createDate;
    /**
     * 创建者Id
     */
    private Long createUserId;
    /**
     * 创建者
     */
    private String createUserName;
    /**
     * 修改日期
     */
    private Date modifyDate;
    /**
     * 修改者ID
     */
    private Long modifyUserId;
    /**
     * 修改者
     */
    private String modifyUserName;
}
