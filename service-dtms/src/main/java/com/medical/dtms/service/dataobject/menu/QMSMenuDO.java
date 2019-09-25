package com.medical.dtms.service.dataobject.menu;

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
 * @version： QMSMenuDO.java v 1.0, 2019年08月14日 10:52 wuxuelin Exp$
 * @Description  模块（菜单导航）
 **/
@Data
@Table(name = "tb_dtms_qms_menu")
public class QMSMenuDO {
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
     * 上级模块
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    @LogTag(alias = "account", builtinType = BuiltinTypeHandler.NORMAL)
    private Long parentId;
    /**
     * 模块编号
     */
    @LogTag(alias = "account", builtinType = BuiltinTypeHandler.NORMAL)
    private String code;
    /**
     * 模块名称
     */
    @LogTag(alias = "account", builtinType = BuiltinTypeHandler.NORMAL)
    private String fullName;
    /**
     * 描述
     */
    @LogTag(alias = "account", builtinType = BuiltinTypeHandler.NORMAL)
    private String description;
    /**
     * 菜单图标
     */
    @LogTag(alias = "account", builtinType = BuiltinTypeHandler.NORMAL)
    private String img;
    /**
     * 类别
     */
    @LogTag(alias = "account", builtinType = BuiltinTypeHandler.NORMAL)
    private String category;
    /**
     * 导航Url
     */
    @LogTag(alias = "account", builtinType = BuiltinTypeHandler.NORMAL)
    private String navigateUrl;
    /**
     * 链接目标
     */
    @LogTag(alias = "account", builtinType = BuiltinTypeHandler.NORMAL)
    private String target;
    /**
     * 是否展开 true - 是 false - 否
     */
    @LogTag(alias = "account", builtinType = BuiltinTypeHandler.NORMAL)
    private Boolean isUnfold;
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
     * 是否有效 true - 是 false - 否
     */
    @LogTag(alias = "account", builtinType = BuiltinTypeHandler.NORMAL)
    private Boolean enabled;
    /**
     * 排序码
     */
    @LogTag(alias = "account", builtinType = BuiltinTypeHandler.NORMAL)
    private String sortCode;
    /**
     * 删除 true - 是 false - 否
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