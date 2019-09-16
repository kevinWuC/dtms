package com.medical.dtms.common.model.file;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;
import lombok.Data;

import java.util.Date;

/**
 * @version： ApplyCheckModel.java v 1.0, 2019年09月10日 10:16 wuxuelin Exp$
 * @Description 申请审核 列表页面
 **/
@Data
public class ApplyCheckModel {

    /**
     * 业务主键
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long bizId;
    /**
     * 文件 id
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long fileId;
    /**
     * 操作类别
     */
    private Integer fileApplyType;
    /**
     * 操作类别 name
     */
    private String fileApplyTypeName;
    /**
     * 申请类别 name
     */
    private String applyTypeName;
    /**
     * 申请原因 name
     */
    private String applyReasonName;
    /**
     * 申请部门 name
     */
    private String applyDeptName;
    /**
     * 申请时间
     */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date applyTime;
    /**
     * 文件编号
     */
    private String fileNo;
    /**
     * 版本号
     */
    private String fileVersion;
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 文件类别 name
     */
    private String fileTypeIdName;
    /**
     * 编写人 name
     */
    private String applyUserName;
    /**
     * 审核人 name
     */
    private String approveUserName;
    /**
     * 审核时间
     */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date approveTime;
    /**
     * 审批人 name
     */
    private String auditorName;
    /**
     * 是否结束
     */
    private Boolean isFinish;
    /**
     * 是否通过
     */
    private Boolean result;
    /**
     * 审核意见
     */
    private String approverContent;
}
