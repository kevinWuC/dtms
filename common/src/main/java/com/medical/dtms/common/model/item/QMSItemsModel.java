package com.medical.dtms.common.model.item;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;
import lombok.Data;

import java.util.List;

/**
 * @version： QMSItemsModel.java v 1.0, 2019年08月15日 14:23 wuxuelin Exp$
 * @Description 数据字典主表model
 **/
@Data
public class QMSItemsModel {

    /**
     * 业务主键
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long bizId;
    /**
     * 编号
     */
    private String code;
    /**
     * 名称
     */
    private String fullName;
    /**
     * 父节点主键
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long parentId;
    /**
     * 分类
     */
    private String category;
    /**
     * 有效：1-有效，0-无效
     */
    private Boolean enabled;
    /**
     * 允许编辑
     */
    private Boolean allowEdit;
    /**
     * 允许删除
     */
    private Boolean allowDelete;
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
    private List<QMSItemsModel> list;
}
