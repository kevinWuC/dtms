package com.medical.dtms.service.dataobject.datasource;

import lombok.Data;

import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "tb_dtms_qms_back_up")
public class QMSBackUpDO {
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
     * 定时自动备份执行时间
     */
    private Date excDate;
    /**
     * 是否有效 true 有效  false 无效
     */
    private Boolean effective;
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
}