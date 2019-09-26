package com.medical.dtms.service.dataobject.role;

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
 * @version： QMSRoleDO.java v 1.0, 2019年08月14日 10:52 wuxuelin Exp$
 * @Description  角色
 **/
@Data
@Table(name = "tb_dtms_qms_role")
public class QMSRoleDO {
    /**
     * 自增id
     */
    private Long id;
    /**
     * 业务主键
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long bizId;
    /**
     * 角色名称
     */
    @LogTag(alias = "account", builtinType = BuiltinTypeHandler.NORMAL)
    private String roleName;
    /**
     * 允许查询 true - 是 false - 否
     */
    @LogTag(alias = "account", builtinType = BuiltinTypeHandler.NORMAL)
    private Boolean allowQuery;
    /**
     * 允许只读 true - 是 false - 否
     */
    @LogTag(alias = "account", builtinType = BuiltinTypeHandler.NORMAL)
    private Boolean allowRead;
    /**
     * 允许编辑 true - 是 false - 否
     */
    @LogTag(alias = "account", builtinType = BuiltinTypeHandler.NORMAL)
    private Boolean allowEdit;
    /**
     * 允许删除 true - 是 false - 否
     */
    @LogTag(alias = "account", builtinType = BuiltinTypeHandler.NORMAL)
    private Boolean allowDelete;
    /**
     * 允许作废 true - 是 false - 否
     */
    @LogTag(alias = "account", builtinType = BuiltinTypeHandler.NORMAL)
    private Boolean allowBad;
    /**
     * 允许上传 true - 是 false - 否
     *
     */
    @LogTag(alias = "account", builtinType = BuiltinTypeHandler.NORMAL)
    private Boolean allowUpload;
    /**
     * 允许下载 true - 是 false - 否
     */
    @LogTag(alias = "account", builtinType = BuiltinTypeHandler.NORMAL)
    private Boolean allowDown;
    /**
     * 允许审核 true - 是 false - 否
     */
    @LogTag(alias = "account", builtinType = BuiltinTypeHandler.NORMAL)
    private Boolean allowAudit;
    /**
     * 允许审批 true - 是 false - 否
     */
    @LogTag(alias = "account", builtinType = BuiltinTypeHandler.NORMAL)
    private Boolean allowApprove;
    /**
     * 允许接受 true - 是 false - 否
     */
    @LogTag(alias = "account", builtinType = BuiltinTypeHandler.NORMAL)
    private Boolean allowReceive;
    /**
     * 允许授权 true - 是 false - 否
     */
    @LogTag(alias = "account", builtinType = BuiltinTypeHandler.NORMAL)
    private Boolean allowAuthorization;
    /**
     * 允许打印 true - 是 false - 否
     */
    @LogTag(alias = "account", builtinType = BuiltinTypeHandler.NORMAL)
    private Boolean allowPrint;
    /**
     * 允许归档 true - 是 false - 否
     */
    @LogTag(alias = "account", builtinType = BuiltinTypeHandler.NORMAL)
    private Boolean allowArchive;
    /**
     * 允许配置 true - 是 false - 否
     */
    @LogTag(alias = "account", builtinType = BuiltinTypeHandler.NORMAL)
    private Boolean allowConfigure;
    /**
     * 允许启用 true - 是 false - 否
     */
    @LogTag(alias = "account", builtinType = BuiltinTypeHandler.NORMAL)
    private Boolean allowEnable;
    /**
     * 允许冻结 true - 是 false - 否
     */
    @LogTag(alias = "account", builtinType = BuiltinTypeHandler.NORMAL)
    private Boolean allowFreeze;
    /**
     * 年审 true - 是 false - 否
     */
    @LogTag(alias = "account", builtinType = BuiltinTypeHandler.NORMAL)
    private Boolean allowYear;
    /**
     * 仪器设置/授权 true - 是 false - 否
     */
    @LogTag(alias = "account", builtinType = BuiltinTypeHandler.NORMAL)
    private Boolean allowDevice;
    /**
     * 允许下载源文件 true - 是 false - 否
     */
    @LogTag(alias = "account", builtinType = BuiltinTypeHandler.NORMAL)
    private Boolean allowDownSourceFile;
    /**
     * 排序码
     */
    @LogTag(alias = "account", builtinType = BuiltinTypeHandler.NORMAL)
    private String sortCode;
    /**
     * true - 未删除  false - 删除
     */
    @LogTag(alias = "account", builtinType = BuiltinTypeHandler.NORMAL)
    private Boolean isDeleted;
    /**
     * 创建人
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
     * 修改人
     */
    private String modifier;
    /**
     * 修改人id
     */
    private String modifierId;
    /**
     * 修改时间
     */
    private Date gmtModified;
}