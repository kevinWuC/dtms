package com.medical.dtms.common.model.file;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;
import com.medical.dtms.common.model.user.SimpleUserModel;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @version： FileDetailModel.java v 1.0, 2019年09月11日 14:55 wuxuelin Exp$
 * @Description 文件 model
 **/
@Data
public class FileDetailModel {

    /**
     * 业务主键
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long bizId;
    /**
     * 文件编号
     */
    private String fileNo;
    /**
     * 文件名称
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
     * 版本号
     */
    private String fileVersion;
    /**
     * 申请类别id
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long applyType;
    /**
     * 申请类别name
     */
    private String applyTypeName;
    /**
     * 审核人ID
     */
    private String approverUserId;
    /**
     * 审核人 name
     */
    private String approverUserName;
    /**
     * 申请日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date applyDate;
    /**
     * 生效日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date effectDate;
    /**
     * 文件页码大小
     */
    private Integer officePageSie;
    /**
     * 申请部门（编制部门）ID
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long applyDeptId;
    /**
     * 申请部门 name
     */
    private String applyDeptName;
    /**
     * 申请人id
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long applyUserId;
    /**
     * 申请人name
     */
    private String applyUserName;
    /**
     * 申请原因id
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long applyReasonId;
    /**
     * 修改原因 name
     */
    private String applyReasonName;
    /**
     * 审批人id
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long auditorId;
    /**
     * 审批人姓名
     */
    private String auditorName;
    /**
     * 编写依据
     */
    private String fileInfo;
    /**
     * 是否年审 true 年审 false 不年审
     */
    private Boolean isAnnualVerification;
    /**
     * 年审周期
     */
    private Integer yearAuditCycle;
    /**
     * 年审部门id
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long annualVerificationDeptId;
    /**
     * 年审部门 name
     */
    private String annualVerificationDeptName;
    /**
     * 文件存储名称和或路径
     */
    private String fileContent;
    private List<SimpleFileAttachmentModel> fileContentList;
    /**
     * 会签人1
     */
    private String signator1Id;
    /**
     * 会签人2
     */
    private String signator2Id;
    /**
     * 批准人
     */
    private String approverId;
    /**
     * 会签人1集合
     */
    private List<SimpleUserModel> signator1;
    /**
     * 会签人2集合
     */
    private List<SimpleUserModel> signator2;
    /**
     * 批准人集合
     */
    private List<SimpleUserModel> approverList;
}
