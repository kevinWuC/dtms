package com.medical.dtms.common.model.item;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;
import lombok.Data;

/**
 * @version： SimpleQMSItemDetailsModel.java v 1.0, 2019年10月12日 9:48 wuxuelin Exp$
 * @Description
 **/
@Data
public class SimpleQMSItemDetailsModel {

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

}
