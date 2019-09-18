package com.medical.dtms.common.model.datasource;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @version： BackUpInfoModel.java v 1.0, 2019年09月16日 17:09 wuxuelin Exp$
 * @Description 数据库备份文件 信息
 **/
@Data
public class BackUpInfoModel {
    /**
     * 业务主键
     */
    private String bizId;
    /**
     * 备份文件文件名
     */
    private String sqlFileName;
    /**
     * 文件大小
     */
    private String sqlFileSize;
    /**
     * 备份日期
     */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date backUpDate;
    /**
     * 文件url
     */
    private String sqlUrl;
}
