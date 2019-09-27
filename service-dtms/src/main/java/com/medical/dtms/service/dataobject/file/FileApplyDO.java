package com.medical.dtms.service.dataobject.file;

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
@Table(name = "tb_dtms_file_apply")
public class FileApplyDO {
    /**
     * 主键id
     */
    private Long id;
    /**
     * 业务主键
     */
    @LogTag(alias = "bizId", builtinType = BuiltinTypeHandler.NORMAL)
    private Long bizId;
    /**
     * 文件ID
     */
    @LogTag(alias = "fileId", builtinType = BuiltinTypeHandler.NORMAL)
    private Long fileId;
    /**
     * 流程id
     */
    @LogTag(alias = "logId", builtinType = BuiltinTypeHandler.NORMAL)
    private Long logId;
    /**
     * 文件编号
     */
    @LogTag(alias = "fileNo", builtinType = BuiltinTypeHandler.NORMAL)
    private String fileNo;
    /**
     * 文件名
     */
    @LogTag(alias = "fileName", builtinType = BuiltinTypeHandler.NORMAL)
    private String fileName;
    /**
     * 文件类别id
     */
    @LogTag(alias = "fileTypeId", builtinType = BuiltinTypeHandler.NORMAL)
    private Long fileTypeId;
    /**
     * 申请类别id
     */
    @LogTag(alias = "applyType", builtinType = BuiltinTypeHandler.NORMAL)
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long applyType;
    /**
     * 文件报批类型 1 文件新增 2 文件修改 3 文件归档 4 未知
     */
    @LogTag(alias = "fileApplyType", builtinType = BuiltinTypeHandler.NORMAL)
    private Integer fileApplyType;
    /**
     * 申请日期
     */
    @LogTag(alias = "applyDate", builtinType = BuiltinTypeHandler.NORMAL)
    private String applyDate;
    /**
     * 文件版本
     */
    @LogTag(alias = "fileVersion", builtinType = BuiltinTypeHandler.NORMAL)
    private String fileVersion;
    /**
     * 生效日期
     */
    @LogTag(alias = "effectDate", builtinType = BuiltinTypeHandler.NORMAL)
    private String effectDate;
    /**
     * 申请部门
     */
    @LogTag(alias = "applyDeptId", builtinType = BuiltinTypeHandler.NORMAL)
    private Long applyDeptId;
    /**
     * 申请部门
     */
    @LogTag(alias = "applyDeptName", builtinType = BuiltinTypeHandler.NORMAL)
    private String applyDeptName;
    /**
     * 本次申请的制定部门 id
     */
    @LogTag(alias = "makeDeptId", builtinType = BuiltinTypeHandler.NORMAL)
    private Long makeDeptId;
    /**
     * 本次申请的制定部门 name
     */
    @LogTag(alias = "makeDeptName", builtinType = BuiltinTypeHandler.NORMAL)
    private String makeDeptName;
    /**
     * 修改原因id
     */
    @LogTag(alias = "applyReasonId", builtinType = BuiltinTypeHandler.NORMAL)
    private Long applyReasonId;
    /**
     * 编写人 id
     */
    @LogTag(alias = "applyUserId", builtinType = BuiltinTypeHandler.NORMAL)
    private Long applyUserId;
    /**
     * 编写人 姓名
     */
    @LogTag(alias = "applyUserName", builtinType = BuiltinTypeHandler.NORMAL)
    private String applyUserName;
    /**
     * 审核人id
     */
    @LogTag(alias = "auditUserId", builtinType = BuiltinTypeHandler.NORMAL)
    private String auditUserId;
    /**
     * 审核人姓名
     */
    @LogTag(alias = "auditUserName", builtinType = BuiltinTypeHandler.NORMAL)
    private String auditUserName;
    /**
     * 审批人id
     */
    @LogTag(alias = "approverUserId", builtinType = BuiltinTypeHandler.NORMAL)
    private String approverUserId;
    /**
     * 审批人姓名
     */
    @LogTag(alias = "approverUserName", builtinType = BuiltinTypeHandler.NORMAL)
    private String approverUserName;
    /**
     * 修改内容
     */
    @LogTag(alias = "content", builtinType = BuiltinTypeHandler.NORMAL)
    private String content;
    /**
     * 审批日期
     */
    @LogTag(alias = "approverDate", builtinType = BuiltinTypeHandler.NORMAL)
    private String approverDate;
    /**
     * 审核结果
     */
    @LogTag(alias = "result", builtinType = BuiltinTypeHandler.NORMAL)
    private Boolean result;
    /**
     * 是否结束
     */
    @LogTag(alias = "isFinish", builtinType = BuiltinTypeHandler.NORMAL)
    private Boolean isFinish;
    /**
     * 变更前
     */
    @LogTag(alias = "beforeChange", builtinType = BuiltinTypeHandler.NORMAL)
    private String beforeChange;
    /**
     * 变更后
     */
    @LogTag(alias = "afterChange", builtinType = BuiltinTypeHandler.NORMAL)
    private String afterChange;
    /**
     * 是否删除
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