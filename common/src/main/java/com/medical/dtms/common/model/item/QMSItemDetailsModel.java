package com.medical.dtms.common.model.item;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;
import lombok.Data;

/**
 * @version： QMSItemDetailsModel.java v 1.0, 2019年08月16日 15:59 wuxuelin Exp$
 * @Description 数据字典明细 model
 **/
@Data
public class QMSItemDetailsModel {

    /**
     * 业务主键
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long bizId;
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
}
