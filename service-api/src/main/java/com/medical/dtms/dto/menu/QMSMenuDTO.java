package com.medical.dtms.dto.menu;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;
import lombok.Data;

import java.util.Date;

/**
 * @version： QMSMenuDTO.java v 1.0, 2019年08月14日 10:52 wuxuelin Exp$
 * @Description 模块（菜单导航）
 **/
@Data
public class QMSMenuDTO {
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
    private Long parentId;
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
     * 是否折叠 true - 是 false - 否
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
     * 删除 true - 是 false - 否
     */
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