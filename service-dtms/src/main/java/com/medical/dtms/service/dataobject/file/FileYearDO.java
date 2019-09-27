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
@Table(name = "tb_dtms_file_year")
public class FileYearDO {
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
     * 文件id
     */
    @LogTag(alias = "fileId", builtinType = BuiltinTypeHandler.NORMAL)
    private Long fileId;
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
    @LogTag(alias = "fileTypeId", builtinType = BuiltinTypeHandler.NORMAL)
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long fileTypeId;
    /**
     * 版本号
     */
    @LogTag(alias = "fileVersion", builtinType = BuiltinTypeHandler.NORMAL)
    private String fileVersion;
    /**
     * 流程id
     */
    @LogTag(alias = "logId", builtinType = BuiltinTypeHandler.NORMAL)
    private Long logId;
    /**
     * 版本有效性 1 是 0否
     */
    @LogTag(alias = "versionEffect", builtinType = BuiltinTypeHandler.NORMAL)
    private Boolean versionEffect;
    /**
     * 修订内容
     */
    @LogTag(alias = "versionEffectInfo", builtinType = BuiltinTypeHandler.NORMAL)
    private String versionEffectInfo;
    /**
     * 技术先进性
     */
    @LogTag(alias = "advancement", builtinType = BuiltinTypeHandler.NORMAL)
    private Boolean advancement;
    /**
     * 修订内容
     */
    @LogTag(alias = "advancementInfo", builtinType = BuiltinTypeHandler.NORMAL)
    private String advancementInfo;
    /**
     * 格式规范化
     */
    @LogTag(alias = "format", builtinType = BuiltinTypeHandler.NORMAL)
    private Boolean format;
    /**
     * 修订内容
     */
    @LogTag(alias = "formatInfo", builtinType = BuiltinTypeHandler.NORMAL)
    private String formatInfo;
    /**
     * 执行可操作性
     */
    @LogTag(alias = "operability", builtinType = BuiltinTypeHandler.NORMAL)
    private Boolean operability;
    /**
     * 修订内容
     */
    @LogTag(alias = "operabilityInfo", builtinType = BuiltinTypeHandler.NORMAL)
    private String operabilityInfo;
    /**
     * 内容适用性
     */
    @LogTag(alias = "applicability", builtinType = BuiltinTypeHandler.NORMAL)
    private Boolean applicability;
    /**
     * 修订内容
     */
    @LogTag(alias = "applicabilityInfo", builtinType = BuiltinTypeHandler.NORMAL)
    private String applicabilityInfo;
    /**
     * 评审其他
     */
    @LogTag(alias = "examinedOther", builtinType = BuiltinTypeHandler.NORMAL)
    private String examinedOther;
    /**
     * 评审日期
     */
    @LogTag(alias = "examinedDate", builtinType = BuiltinTypeHandler.NORMAL)
    private Date examinedDate;
    /**
     * 评审人ID
     */
    @LogTag(alias = "examinedUserId", builtinType = BuiltinTypeHandler.NORMAL)
    private String examinedUserId;
    /**
     * 评审人
     */
    @LogTag(alias = "examinedUserName", builtinType = BuiltinTypeHandler.NORMAL)
    private String examinedUserName;
    /**
     * 评审状态
     */
    @LogTag(alias = "examinedStatus", builtinType = BuiltinTypeHandler.NORMAL)
    private Boolean examinedStatus;
    /**
     * 年审结果
     */
    @LogTag(alias = "isYearApprove", builtinType = BuiltinTypeHandler.NORMAL)
    private Boolean isYearApprove;
    /**
     * 生效时间
     */
    @LogTag(alias = "effectDate", builtinType = BuiltinTypeHandler.NORMAL)
    private Date effectDate;
    /**
     * 应用部门 id
     */
    @LogTag(alias = "applyDeptId", builtinType = BuiltinTypeHandler.NORMAL)
    private String applyDeptId;
    /**
     * 年审部门id
     */
    @LogTag(alias = "annualVerificationDeptId", builtinType = BuiltinTypeHandler.NORMAL)
    private String annualVerificationDeptId;
    /**
     * 年审部门名
     */
    @LogTag(alias = "annualVerificationDeptName", builtinType = BuiltinTypeHandler.NORMAL)
    private String annualVerificationDeptName;
    /**
     * 年审结论
     */
    @LogTag(alias = "yearConclusion", builtinType = BuiltinTypeHandler.NORMAL)
    private Boolean yearConclusion;
    /**
     * 创建人
     */
    private String creator;
    /**
     * 是否删除 false 否 true 是
     */
    @LogTag(alias = "isDeleted", builtinType = BuiltinTypeHandler.NORMAL)
    private Boolean isDeleted;
    /**
     * 创建人ID
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
     * 修改人ID
     */
    private String modifierId;
    /**
     * 修改时间
     */
    private Date gmtModified;
}