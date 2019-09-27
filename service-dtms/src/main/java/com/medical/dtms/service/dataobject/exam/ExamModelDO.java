package com.medical.dtms.service.dataobject.exam;

import java.util.Date;

import com.medical.dtms.logclient.annotation.LogTag;
import com.medical.dtms.logclient.handler.BuiltinTypeHandler;
import lombok.Data;

import javax.persistence.Table;

/***
 * 试卷信息
 *
 * @author shenqifeng
 * @version $Id: ExamModelDO.java, v 0.1 2019年9月7日 下午8:08:36 shenqifeng Exp $
 */
@Data
@Table(name = "tb_dtms_exam_model")
public class ExamModelDO {
    /**
     * 主键
     */
    private Long id;
    /**
     * 试卷id
     */
    @LogTag(alias = "examId", builtinType = BuiltinTypeHandler.NORMAL)
    private Long examId;
    /**
     * 试卷名称
     */
    @LogTag(alias = "examName", builtinType = BuiltinTypeHandler.NORMAL)
    private String examName;
    /**
     * 考试时长
     */
    @LogTag(alias = "examDuration", builtinType = BuiltinTypeHandler.NORMAL)
    private Integer examDuration;
    /**
     * 总分
     */
    @LogTag(alias = "totalPoints", builtinType = BuiltinTypeHandler.NORMAL)
    private Integer totalPoints;
    /**
     * 是否使用中
     */
    @LogTag(alias = "isUse", builtinType = BuiltinTypeHandler.NORMAL)
    private Boolean isUse;
    /**
     * 是否培训
     */
    @LogTag(alias = "isPeixun", builtinType = BuiltinTypeHandler.NORMAL)
    private Boolean isPeixun;
    /***/
    private Date createDate;
    /***/
    private Long createUserId;
    /***/
    private String createUserName;
    /***/
    private Date modifyDate;
    /***/
    private Long modifyUserId;
    /***/
    private String modifyUserName;
    /**
     * 是否删除
     */
    @LogTag(alias = "isDeleted", builtinType = BuiltinTypeHandler.NORMAL)
    private Boolean isDeleted;

}