package com.medical.dtms.dto.file;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;
import lombok.Data;

import java.util.Date;

/**
 * @version： FileApplyDTO.java v 1.0, 2019年08月30日 16:02 wuxuelin Exp$
 * @Description 文件申请 实体类
 **/
@Data
public class FileApplyDTO {
    /**
     * 主键id
     */
    private Long id;
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
     * 申请类别id
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long applyType;
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
     * 原文件的申请/编制部门id
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long applyDeptId;
    /**
     * 原文件的申请/编制部门 name
     */
    private String applyDeptName;
    /**
     * 本次申请的制定部门 id
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long makeDeptId;
    /**
     * 本次申请的制定部门 name
     */
    private String makeDeptName;
    /**
     * 修改原因id
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long applyReasonId;
    /**
     * 编写人 id
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long applyUserId;
    /**
     * 编写人 姓名
     */
    private String applyUserName;
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
     * 提交类型 7 申请修改 8 申请作废 9 修改
     */
    private Integer submitType;
    /**
     * 是否删除
     */
    private Boolean isDeleted;
    /**
     * 创建人
     */
    private String creator;
    /**
     * 创建人id
     */
    private String creatorId;
    /**
     * 创建时间
     */
    private Date gmtCreated;
    /**
     * 修改人
     */
    private String modifier;
    /**
     * 修改人id
     */
    private String modifierId;
    /**
     * 修改时间
     */
    private Date gmtModified;
}
