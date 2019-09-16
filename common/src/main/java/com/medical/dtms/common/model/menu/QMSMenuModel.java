package com.medical.dtms.common.model.menu;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @version： QMSMenuModel.java v 1.0, 2019年08月20日 10:12 wuxuelin Exp$
 * @Description 菜单model
 **/
@Data
public class QMSMenuModel implements Serializable {

    private static final long serialVersionUID = -8958034110238015847L;
    /**
     * 业务主键
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long bizId;
    /**
     * 上级模块id
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long parentId;
    /**
     * 上级模块名称
     */
    private String parentName;
    /**
     * 模块编号
     */
    private String code;
    /**
     * 模块名称
     */
    private String fullName;
    /**
     * 描述
     */
    private String description;
    /**
     * 菜单图标
     */
    private String img;
    /**
     * 类别
     */
    private String category;
    /**
     * 导航Url
     */
    private String navigateUrl;
    /**
     * 链接目标
     */
    private String target;
    /**
     * 是否展开 true - 是 false - 否
     */
    private Boolean isUnfold;
    /**
     * 允许编辑 true - 是 false - 否
     */
    private Boolean allowEdit;
    /**
     * 允许删除 true - 是 false - 否
     */
    private Boolean allowDelete;
    /**
     * 是否有效 true - 是 false - 否
     */
    private Boolean enabled;
    /**
     * 排序码
     */
    private String sortCode;
    /**
     * 是否为末级 true 是 false 否
     */
    private Boolean lastOrNot;
    /**
     * 下一级 列表
     */
    private List<QMSMenuModel> list;
    /**
     * 角色 id
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long roleId;
}
