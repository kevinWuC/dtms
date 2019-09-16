package com.medical.dtms.dto.item;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;
import lombok.Data;

import java.util.Date;

/**
 * @version： QMSItemDetailsDTO.java v 1.0, 2019年08月14日 10:52 wuxuelin Exp$
 * @Description 数据字典明细表
 **/
@Data
public class QMSItemDetailsDTO {

    /**
     * 自增 id
     */
    private Long id;
    /**
     * 业务主键
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long bizId;
    /**
     * 字典列表 id
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long itemsId;
    /**
     * 名称
     */
    private String itemName;
    /**
     * 代码
     */
    private String itemCode;
    /**
     * 描述
     */
    private String description;
    /**
     * 是否有效 true 是 false 否
     */
    private Boolean enabled;
    /**
     * 允许编辑 true 是 false 否
     */
    private Boolean allowEdit;
    /**
     * 允许删除 true 是 false 否
     */
    private Boolean allowDelete;
    /**
     * 排序码
     */
    private String sortCode;
    /**
     * 删除标记:false-正常，true-删除
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