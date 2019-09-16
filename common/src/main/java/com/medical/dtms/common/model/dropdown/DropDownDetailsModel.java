package com.medical.dtms.common.model.dropdown;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;
import lombok.Data;

/**
 * @version： DropDownDetailsModel.java v 1.0, 2019年09月10日 14:16 huangshuaiquan Exp$
 * @Description
 **/
@Data
public class DropDownDetailsModel {

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
