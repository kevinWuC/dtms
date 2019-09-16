package com.medical.dtms.common.model.file;

import lombok.Data;

/**
 * @version： FileYearRecordExportModel.java v 1.0, 2019年09月01日 22:08 wuxuelin Exp$
 * @Description 年审记录导出 model
 **/
@Data
public class FileYearRecordExportModel {

    /**
     * 文件编号
     */
    private String fileNo;
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 版本/修订号
     */
    private String fileVersion;
    /**
     * 生效日期
     */
    private String effectDate;
    /**
     * 年审日期
     */
    private String examinedDate;
    /**
     * 年审结论
     */
    private String yearConclusion;
    /**
     * 版本有效性
     */
    private String versionEffect;
    /**
     * 修订内容
     */
    private String versionEffectInfo;
    /**
     * 技术先进性
     */
    private String advancement;
    /**
     * 修订内容1
     */
    private String advancementInfo;
    /**
     * 格式规范化
     */
    private String format;
    /**
     * 修订内容2
     */
    private String formatInfo;
    /**
     * 执行可操作性
     */
    private String operability;
    /**
     * 修订内容3
     */
    private String operabilityInfo;
    /**
     * 内容适用性
     */
    private String applicability;
    /**
     * 修订内容4
     */
    private String applicabilityInfo;
    /**
     * 其他
     */
    private String examinedOther;
    /**
     * 评审人
     */
    private String examinedUserName;

}
