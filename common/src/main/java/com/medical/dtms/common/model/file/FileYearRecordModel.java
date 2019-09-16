package com.medical.dtms.common.model.file;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;
import lombok.Data;

import java.util.Date;

/**
 * @version： FileYearRecordModel.java v 1.0, 2019年08月28日 11:25 wuxuelin Exp$
 * @Description 文件年审 model
 **/
@Data
public class FileYearRecordModel {
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
     * 版本号
     */
    private String fileVersion;
    /**
     * 生效时间
     */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date effectDate;
    /**
     * 评审日期
     */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date examinedDate;
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
     * 评审人
     */
    private String examinedUserName;
    /**
     * 年审结论
     */
    private Boolean yearConclusion;
}
