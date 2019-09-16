package com.medical.dtms.service.dataobject.demo;

import lombok.Data;

import java.util.Date;

@Data
public class LeaveDO {

    /**
     * 自增id
     */
    private Long id;
    /**
     * 业务主键
     */
    private Long bizId;
    /**
     * 人员 id
     */
    private String userId;
    /**
     * 审批内容
     */
    private String content;
    /**
     * 审批人 id
     */
    private String approveId;
    /**
     * 流程实例 id
     */
    private String procInstId;
    /**
     * 流程定义 id
     */
    private String procDefId;
    /**
     * 是否删除 0 正常 1 删除
     */
    private Boolean isDeleted;
    /**
     * 创建人姓名
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
     * 修改人 姓名
     */
    private String modifier;
    /**
     * 修改人 id
     */
    private String modifierId;
    /**
     * 修改时间
     */
    private Date gmtModified;
}
