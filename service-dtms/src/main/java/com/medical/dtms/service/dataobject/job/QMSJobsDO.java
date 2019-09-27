package com.medical.dtms.service.dataobject.job;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;
import com.medical.dtms.logclient.annotation.LogTag;
import com.medical.dtms.logclient.handler.BuiltinTypeHandler;
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
    @LogTag(alias = "bizId", builtinType = BuiltinTypeHandler.NORMAL)
    private Long bizId;
    /**
     * 职位名称
     */
    @LogTag(alias = "jobName", builtinType = BuiltinTypeHandler.NORMAL)
    private String jobName;
    /**
     * 允许查询 true-是 false-否
     */
    @LogTag(alias = "allowQuery", builtinType = BuiltinTypeHandler.NORMAL)
    private Boolean allowQuery;
    /**
     * 允许只读 true-是 false-否
     */
    @LogTag(alias = "allowRead", builtinType = BuiltinTypeHandler.NORMAL)
    private Boolean allowRead;
    /**
     * 允许编辑 true-是 false-否
     */
    @LogTag(alias = "allowEdit", builtinType = BuiltinTypeHandler.NORMAL)
    private Boolean allowEdit;
    /**
     * 允许启用 true-是 false-否
     */
    @LogTag(alias = "allowEnable", builtinType = BuiltinTypeHandler.NORMAL)
    private Boolean allowEnable;
    /**
     * 允许冻结 true-是 false-否
     */
    @LogTag(alias = "allowFreeze", builtinType = BuiltinTypeHandler.NORMAL)
    private Boolean allowFreeze;
    /**
     * 允许删除 true-是 false-否
     */
    @LogTag(alias = "allowDelete", builtinType = BuiltinTypeHandler.NORMAL)
    private Boolean allowDelete;
    /**
     * 排序码
     */
    @LogTag(alias = "sortCode", builtinType = BuiltinTypeHandler.NORMAL)
    private String sortCode;
    /**
     * 部门id
     */
    @LogTag(alias = "deptId", builtinType = BuiltinTypeHandler.NORMAL)
    private String deptId;
    /**
     * 部门名称
     */
    @LogTag(alias = "deptName", builtinType = BuiltinTypeHandler.NORMAL)
    private String deptName;
    /**
     * 删除标记:true-删除，false-正常
     */
    @LogTag(alias = "isDeleted", builtinType = BuiltinTypeHandler.NORMAL)
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