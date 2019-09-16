package com.medical.dtms.dto.file;

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
 * @version： FileModelDTO.java v 1.0, 2019年08月27日 10:54 wuxuelin Exp$
 * @Description 文件 实体类
 **/
@Data
public class FileModelDTO {
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
     * 文件报批类型 1 文件新增 2 文件修改 3 文件归档 4 未知
     */
    private Integer fileApplyType;
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
     * 发布年度
     */
    private Integer applyYear;
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
     * 申请人id
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long applyUserId;
    /**
     * 申请原因id
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long applyReasonId;
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
     * 文件存储名称和或路径
     */
    private String fileContent;
    /**
     * 会签人1 集合
     */
    private List<Long> signator1;
    /**
     * 会签人2 集合
     */
    private List<Long> signator2;
    /**
     * 批准人 集合
     */
    private List<Long> approverList;
    /**
     * 会签人1 集合
     */
    private String signator1Id;
    /**
     * 会签人2 集合
     */
    private String signator2Id;
    /**
     * 批准人集合
     */
    private String approverId;
    /**
     * 文件状态 1 未提交 2 已提交 3 生效 4 归档 5 作废 6 退回 7 待审批
     */
    private Integer fileStatus;
    /**
     * 提交类型 1 保存 2 通过审批 3 退回 4 提交 5 直送
     */
    private Integer submitType;
    /**
     * 审核意见
     */
    private String approverContent;
    /**
     * 生效日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date effectDate;
    /**
     * 被委托人id
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long receiveUserId;
    /**
     * 被委托人 name
     */
    private String receiveUserName;
    /**
     * 是否接收 false 否 true 是
     */
    private Boolean isReceived;
    /**
     * 接收时间
     */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date receiveDate;
    /**
     * 接收者 id
     */
    private String receiverId;
    /**
     * 接收者 name
     */
    private String receiverName;
    /**
     * 1允许发起修改、年审、作废流程
     */
    private Boolean allowModify;
    /**
     * true 允许作废  false 否
     */
    private Boolean allowBad;
    /**
     * 申请状态：默认是0，在申请过程1，结束变为0
     */
    private Integer applyStatus;
    private Integer operateType;
    /**
     * 是否删除 false 否 true 是
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
