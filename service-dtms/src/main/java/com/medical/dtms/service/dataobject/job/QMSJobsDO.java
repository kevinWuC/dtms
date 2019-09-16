package com.medical.dtms.service.dataobject.job;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;
import lombok.Data;

import javax.persistence.Table;
import java.util.Date;

/**
 * @version： QMSJobsDO.java v 1.0, 2019年08月14日 10:52 wuxuelin Exp$
 * @Description  职位 - 主表
 **/
@Data
@Table(name = "tb_dtms_qms_jobs")
public class QMSJobsDO {
    /**
     * 自增主键
     */
    private Long id;
    /**
     * 业务主键
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long bizId;
    /**
     * 职位名称
     */
    private String jobName;
    /**
     * 允许查询 true-是 false-否
     */
    private Boolean allowQuery;
    /**
     * 允许只读 true-是 false-否
     */
    private Boolean allowRead;
    /**
     * 允许编辑 true-是 false-否
     */
    private Boolean allowEdit;
    /**
     * 允许启用 true-是 false-否
     */
    private Boolean allowEnable;
    /**
     * 允许冻结 true-是 false-否
     */
    private Boolean allowFreeze;
    /**
     * 允许删除 true-是 false-否
     */
    private Boolean allowDelete;
    /**
     * 排序码
     */
    private String sortCode;
    /**
     * 部门id
     */
    private String deptId;
    /**
     * 部门名称
     */
    private String deptName;
    /**
     * 删除标记:true-删除，false-正常
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