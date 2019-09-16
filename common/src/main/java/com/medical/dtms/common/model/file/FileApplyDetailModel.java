package com.medical.dtms.common.model.file;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;
import lombok.Data;

import java.util.Date;

/**
 * @version： FileApplyDetailModel.java v 1.0, 2019年09月11日 15:02 wuxuelin Exp$
 * @Description 申请回显
 **/
@Data
public class FileApplyDetailModel {
    /**
     * 业务主键
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long bizId;
    /**
     * 文件ID
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long fileId;
    /**
     * 流程id
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long logId;
    /**
     * 文件编号
     */
    private String fileNo;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件类别id
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long fileTypeId;
    /**
     * 文件类别 name
     */
    private String fileTypeName;
    /**
     * 申请类别id
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long applyType;
    /**
     * 申请类别 name
     */
    private String applyTypeName;
    /**
     * 文件报批类型 1 文件新增 2 文件修改 3 文件归档 4 未知
     */
    private Integer fileApplyType;
    /**
     * 申请日期
     */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date applyDate;
    /**
     * 文件版本
     */
    private String fileVersion;
    /**
     * 生效日期
     */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date effectDate;
    /**
     * 申请部门id
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long applyDeptId;
    /**
     * 申请部门
     */
    private String applyDeptName;
    /**
     * 编写部门
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long makeDeptId;
    /**
     * 编写部门 name
     */
    private String makeDeptName;
    /**
     * 修改原因id
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long applyReasonId;
    /**
     * 修改原因 name
     */
    private String applyReasonName;
    /**
     * 审核人id
     */
    private String auditUserId;
    /**
     * 审核人姓名
     */
    private String auditUserName;
    /**
     * 审批人id
     */
    private String approverUserId;
    /**
     * 审批人姓名
     */
    private String approverUserName;
    /**
     * 修改内容
     */
    private String content;
    /**
     * 审批日期
     */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date approverDate;
    /**
     * 审核结果
     */
    private Boolean result;
    /**
     * 是否结束
     */
    private Boolean isFinish;
    /**
     * 变更前
     */
    private String beforeChange;
    /**
     * 变更后
     */
    private String afterChange;
    /**
     * 编写人
     */
    private String applyUserName;
}
