package com.medical.dtms.common.model.role;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;
import lombok.Data;

import java.util.List;

/**
 * @version： QMSRoleModel.java v 1.0, 2019年08月20日 12:10 wuxuelin Exp$
 * @Description 角色model
 **/
@Data
public class QMSRoleModel {
    /**
     * 业务主键
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long bizId;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 允许查询 true - 是 false - 否
     */
    private Boolean allowQuery;
    /**
     * 允许只读 true - 是 false - 否
     */
    private Boolean allowRead;
    /**
     * 允许编辑 true - 是 false - 否
     */
    private Boolean allowEdit;
    /**
     * 允许删除 true - 是 false - 否
     */
    private Boolean allowDelete;
    /**
     * 允许作废 true - 是 false - 否
     */
    private Boolean allowBad;
    /**
     * 允许上传 true - 是 false - 否
     */
    private Boolean allowUpload;
    /**
     * 允许下载 true - 是 false - 否
     */
    private Boolean allowDown;
    /**
     * 允许审核 true - 是 false - 否
     */
    private Boolean allowAudit;
    /**
     * 允许审批 true - 是 false - 否
     */
    private Boolean allowApprove;
    /**
     * 允许接受 true - 是 false - 否
     */
    private Boolean allowReceive;
    /**
     * 允许授权 true - 是 false - 否
     */
    private Boolean allowAuthorization;
    /**
     * 允许打印 true - 是 false - 否
     */
    private Boolean allowPrint;
    /**
     * 允许归档 true - 是 false - 否
     */
    private Boolean allowArchive;
    /**
     * 允许配置 true - 是 false - 否
     */
    private Boolean allowConfigure;
    /**
     * 允许启用 true - 是 false - 否
     */
    private Boolean allowEnable;
    /**
     * 允许冻结 true - 是 false - 否
     */
    private Boolean allowFreeze;
    /**
     * 年审 true - 是 false - 否
     */
    private Boolean allowYear;
    /**
     * 仪器设置/授权 true - 是 false - 否
     */
    private Boolean allowDevice;
    /**
     * 允许下载源文件 true - 是 false - 否
     */
    private Boolean allowDownSourceFile;
    /**
     * 排序码
     */
    private String sortCode;
    /**
     * 菜单id 集合
     */
    private List<Long> menuIdList;
}
