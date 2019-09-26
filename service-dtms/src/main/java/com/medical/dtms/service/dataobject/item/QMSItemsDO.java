package com.medical.dtms.service.dataobject.item;

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
 * @version： QMSItemsDO.java v 1.0, 2019年08月14日 10:52 wuxuelin Exp$
 * @Description  数据字典主表
 **/
@Data
@Table(name = "tb_dtms_qms_items")
public class QMSItemsDO {
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
     * 编号
     */
    @LogTag(alias = "account", builtinType = BuiltinTypeHandler.NORMAL)
    private String code;
    /**
     * 名称
     */
    @LogTag(alias = "account", builtinType = BuiltinTypeHandler.NORMAL)
    private String fullName;
    /**
     * 父节点主键
     */
    @LogTag(alias = "account", builtinType = BuiltinTypeHandler.NORMAL)
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long parentId;
    /**
     * 分类
     */
    @LogTag(alias = "account", builtinType = BuiltinTypeHandler.NORMAL)
    private String category;
    /**
     * 树型结构
     */
    @LogTag(alias = "account", builtinType = BuiltinTypeHandler.NORMAL)
    private Integer isTree;
    /**
     * 有效：1-有效，0-无效
     */
    @LogTag(alias = "account", builtinType = BuiltinTypeHandler.NORMAL)
    private Boolean enabled;
    /**
     * 允许编辑
     */
    @LogTag(alias = "account", builtinType = BuiltinTypeHandler.NORMAL)
    private Boolean allowEdit;
    /**
     * 允许删除
     */
    @LogTag(alias = "account", builtinType = BuiltinTypeHandler.NORMAL)
    private Boolean allowDelete;
    /**
     * 排序码
     */
    @LogTag(alias = "account", builtinType = BuiltinTypeHandler.NORMAL)
    private String sortCode;
    /**
     * 删除标记:0-正常，1-删除
     */
    @LogTag(alias = "account", builtinType = BuiltinTypeHandler.NORMAL)
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