package com.medical.dtms.dto.datasource;

import lombok.Data;

import java.util.Date;

/**
 * @version： QMSBackUpDTO.java v 1.0, 2019年09月16日 17:49 wuxuelin Exp$
 * @Description 数据库备份文件 dto
 **/
@Data
public class QMSBackUpDTO {

    /**
     * 自增id
     */
    private Long id;
    /**
     * 业务主键
     */
    private Long bizId;
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
    private Date backUpDate;
    /**
     * 文件url
     */
    private String sqlUrl;
    /**
     * 删除标记:0-正常，1-删除
     */
    private Boolean isDeleted;
    /**
     * 创建用户
     */
    private String creator;
    /**
     * 创建用户主键
     */
    private String creatorId;
    /**
     * 创建时间
     */
    private Date gmtCreated;
    /**
     * 修改用户
     */
    private String modifier;
    /**
     * 修改用户主键
     */
    private String modifierId;
    /**
     * 修改时间
     */
    private Date gmtModified;

    private String urlPath;

}
