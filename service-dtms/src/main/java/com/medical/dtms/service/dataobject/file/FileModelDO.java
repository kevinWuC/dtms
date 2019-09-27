package com.medical.dtms.service.dataobject.file;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;
import com.medical.dtms.logclient.annotation.LogTag;
import com.medical.dtms.logclient.handler.BuiltinTypeHandler;
import lombok.Data;

import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "tb_dtms_file_model")
public class FileModelDO {
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
     * 文件编号
     */
    @LogTag(alias = "fileNo", builtinType = BuiltinTypeHandler.NORMAL)
    private String fileNo;
    /**
     * 文件名称
     */
    @LogTag(alias = "fileName", builtinType = BuiltinTypeHandler.NORMAL)
    private String fileName;
    /**
     * 文件类别id
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    @LogTag(alias = "fileTypeId", builtinType = BuiltinTypeHandler.NORMAL)
    private Long fileTypeId;
    /**
     * 版本号
     */
    @LogTag(alias = "fileVersion", builtinType = BuiltinTypeHandler.NORMAL)
    private String fileVersion;
    /**
     * 申请类别id
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    @LogTag(alias = "applyType", builtinType = BuiltinTypeHandler.NORMAL)
    private Long applyType;
    /**
     * 文件报批类型 1 文件新增 2 文件修改 3 文件归档 4 未知
     */
    @LogTag(alias = "fileApplyType", builtinType = BuiltinTypeHandler.NORMAL)
    private Integer fileApplyType;
    /**
     * 审核人ID
     */
    @LogTag(alias = "approverUserId", builtinType = BuiltinTypeHandler.NORMAL)
    private String approverUserId;
    /**
     * 审核人 name
     */
    @LogTag(alias = "approverUserName", builtinType = BuiltinTypeHandler.NORMAL)
    private String approverUserName;
    /**
     * 审核意见
     */
    @LogTag(alias = "approverContent", builtinType = BuiltinTypeHandler.NORMAL)
    private String approverContent;
    /**
     * 生效日期
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    @LogTag(alias = "effectDate", builtinType = BuiltinTypeHandler.NORMAL)
    private Date effectDate;
    /**
     * 申请日期
     */
    @LogTag(alias = "applyDate", builtinType = BuiltinTypeHandler.NORMAL)
    private Date applyDate;
    /**
     * 发布年度
     */
    @LogTag(alias = "applyYear", builtinType = BuiltinTypeHandler.NORMAL)
    private Integer applyYear;
    /**
     * 文件页码大小
     */
    @LogTag(alias = "officePageSie", builtinType = BuiltinTypeHandler.NORMAL)
    private Integer officePageSie;
    /**
     * 申请部门（编制部门）ID
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    @LogTag(alias = "applyDeptId", builtinType = BuiltinTypeHandler.NORMAL)
    private Long applyDeptId;
    /**
     * 申请人id
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    @LogTag(alias = "applyUserId", builtinType = BuiltinTypeHandler.NORMAL)
    private Long applyUserId;
    /**
     * 申请原因id
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    @LogTag(alias = "applyReasonId", builtinType = BuiltinTypeHandler.NORMAL)
    private Long applyReasonId;
    /**
     * 审批人id
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    @LogTag(alias = "auditorId", builtinType = BuiltinTypeHandler.NORMAL)
    private Long auditorId;
    /**
     * 审批人姓名
     */
    @LogTag(alias = "auditorName", builtinType = BuiltinTypeHandler.NORMAL)
    private String auditorName;
    /**
     * 编写依据
     */
    @LogTag(alias = "fileInfo", builtinType = BuiltinTypeHandler.NORMAL)
    private String fileInfo;
    /**
     * 是否年审 true 年审 false 不年审
     */
    @LogTag(alias = "isAnnualVerification", builtinType = BuiltinTypeHandler.NORMAL)
    private Boolean isAnnualVerification;
    /**
     * 年审周期
     */
    @LogTag(alias = "yearAuditCycle", builtinType = BuiltinTypeHandler.NORMAL)
    private Integer yearAuditCycle;
    /**
     * 年审部门 id
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    @LogTag(alias = "annualVerificationDeptId", builtinType = BuiltinTypeHandler.NORMAL)
    private Long annualVerificationDeptId;
    /**
     * 文件存储名称和或路径
     */
    @LogTag(alias = "fileContentUrl", builtinType = BuiltinTypeHandler.NORMAL)
    private String fileContentUrl;
    /**
     * 会签人1 集合
     */
    @LogTag(alias = "signator1Id", builtinType = BuiltinTypeHandler.NORMAL)
    private String signator1Id;
    /**
     * 会签人2 集合
     */
    @LogTag(alias = "signator2Id", builtinType = BuiltinTypeHandler.NORMAL)
    private String signator2Id;
    /**
     * 批准人id集合
     */
    @LogTag(alias = "approverId", builtinType = BuiltinTypeHandler.NORMAL)
    private String approverId;
    /**
     * 文件状态 1 未提交 2 已提交 3 生效 4 归档 5 作废 6 退回 7 待审批
     */
    @LogTag(alias = "fileStatus", builtinType = BuiltinTypeHandler.NORMAL)
    private Integer fileStatus;
    /**
     * 是否接收 false 否 true 是
     */
    @LogTag(alias = "isReceived", builtinType = BuiltinTypeHandler.NORMAL)
    private Boolean isReceived;
    /**
     * 接收时间
     */
    @LogTag(alias = "receiveDate", builtinType = BuiltinTypeHandler.NORMAL)
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date receiveDate;
    /**
     * 接收人 id
     */
    @LogTag(alias = "receiverId", builtinType = BuiltinTypeHandler.NORMAL)
    private String receiverId;
    /**
     * 接收人 name
     */
    @LogTag(alias = "receiverName", builtinType = BuiltinTypeHandler.NORMAL)
    private String receiverName;
    /**
     * 1允许发起修改、年审、作废流程
     */
    @LogTag(alias = "allowModify", builtinType = BuiltinTypeHandler.NORMAL)
    private Boolean allowModify;
    /**
     * true 允许作废  false 否
     */
    @LogTag(alias = "allowBad", builtinType = BuiltinTypeHandler.NORMAL)
    private Boolean allowBad;
    /**
     * 申请状态：默认是0，在申请过程1，结束变为0
     */
    @LogTag(alias = "applyStatus", builtinType = BuiltinTypeHandler.NORMAL)
    private Integer applyStatus;
    /**
     * 是否删除 false 否 true 是
     */
    @LogTag(alias = "isDeleted", builtinType = BuiltinTypeHandler.NORMAL)
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