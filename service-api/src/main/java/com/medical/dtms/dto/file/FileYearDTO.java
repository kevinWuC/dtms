package com.medical.dtms.dto.file;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;
import lombok.Data;

import java.util.Date;

/**
 * @version： FileYearDTO.java v 1.0, 2019年08月28日 11:25 wuxuelin Exp$
 * @Description 文件年审 实体类
 **/
@Data
public class FileYearDTO {
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
     * 文件id
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long fileId;
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
     * 版本有效性 1 是 0否
     */
    private Boolean versionEffect;
    /**
     * 修订内容
     */
    private String versionEffectInfo;
    /**
     * 技术先进性
     */
    private Boolean advancement;
    /**
     * 修订内容
     */
    private String advancementInfo;
    /**
     * 格式规范化
     */
    private Boolean format;
    /**
     * 修订内容
     */
    private String formatInfo;
    /**
     * 执行可操作性
     */
    private Boolean operability;
    /**
     * 修订内容
     */
    private String operabilityInfo;
    /**
     * 内容适用性
     */
    private Boolean applicability;
    /**
     * 修订内容
     */
    private String applicabilityInfo;
    /**
     * 评审其他
     */
    private String examinedOther;
    /**
     * 评审日期
     */
    private Date examinedDate;
    /**
     * 评审人ID
     */
    private String examinedUserId;
    /**
     * 评审人
     */
    private String examinedUserName;
    /**
     * 评审状态
     */
    private Boolean examinedStatus;
    /**
     * 年审结果
     */
    private Boolean isYearApprove;
    /**
     * 生效时间
     */
    private Date effectDate;
    /**
     * 应用部门 id
     */
    private String applyDeptId;
    /**
     * 年审部门id
     */
    private String annualVerificationDeptId;
    /**
     * 年审部门名
     */
    private String annualVerificationDeptName;
    /**
     * 年审结论
     */
    private Boolean yearConclusion;
    /**
     * 创建人
     */
    private String creator;
    /**
     * 是否删除 false 否 true 是
     */
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