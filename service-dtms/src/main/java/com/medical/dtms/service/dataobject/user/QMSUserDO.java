package com.medical.dtms.service.dataobject.user;

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
 * @version： QMSUserDO.java v 1.0, 2019年08月14日 10:52 wuxuelin Exp$
 * @Description  用户表
 **/
@Data
@Table(name = "tb_dtms_qms_user")
public class QMSUserDO {
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
     * 用户账号
     */
    @LogTag(alias = "account", builtinType = BuiltinTypeHandler.NORMAL)
    private String account;
    /**
     * 用户姓名
     */
    @LogTag(alias = "dspName", builtinType = BuiltinTypeHandler.NORMAL)
    private String dspName;
    /**
     * 部门ID
     */
    @LogTag(alias = "deptId", builtinType = BuiltinTypeHandler.NORMAL)
    private Long deptId;
    /**
     * 角色ID
     */
    @LogTag(alias = "roleId", builtinType = BuiltinTypeHandler.NORMAL)
    private Long roleId;
    /**
     * 密码
     */
    @LogTag(alias = "passWord", builtinType = BuiltinTypeHandler.NORMAL)
    private String passWord;
    /**
     * 是否启用 true 是 false 否
     */
    @LogTag(alias = "allowEnable", builtinType = BuiltinTypeHandler.NORMAL)
    private Boolean allowEnable;
    /**
     * 排序码
     */
    @LogTag(alias = "sortCode", builtinType = BuiltinTypeHandler.NORMAL)
    private String sortCode;
    /**
     * 登录次数
     */
    private Integer logOnCount;
    /**
     * 前一次访问时间
     */
    private Date previousVisit;
    /**
     * 最后一次访问时间
     */
    private Date lastVisit;
    /**
     * 电话
     */
    @LogTag(alias = "mobile", builtinType = BuiltinTypeHandler.NORMAL)
    private String mobile;
    /**
     * 邮箱
     */
    @LogTag(alias = "email", builtinType = BuiltinTypeHandler.NORMAL)
    private String email;
    /**
     * QQ
     */
    @LogTag(alias = "qq", builtinType = BuiltinTypeHandler.NORMAL)
    private String qq;
    /**
     * 微信
     */
    @LogTag(alias = "wx", builtinType = BuiltinTypeHandler.NORMAL)
    private String wx;
    /**
     * false-未删除  true-删除
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