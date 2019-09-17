package com.medical.dtms.service.dataobject.datasource;

import lombok.Data;

import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "tb_dtms_qms_task")
public class QMSTaskDO {
    /**
     * 自增id
     */
    private Long id;
    /**
     * 业务主键
     */
    private Long bizId;
    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 定时自动备份执行时间
     */
    private Integer excDate;
    /**
     * 是否有效 1有效  0无效
     */
    private Boolean effective;
    /**
     * 路径
     */
    private String urlPath;
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
}