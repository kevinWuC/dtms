package com.medical.dtms.common.model.dropdown.query;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;
import lombok.Data;

/**
 * @version： DropDownQuery.java v 1.0, 2019年09月04日 15:10 wuxuelin Exp$
 * @Description 下拉查询实体类
 **/
@Data
public class DropDownQuery {

    /**
     * 字典列表 id
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long itemsId;
    /**
     * 当前页码
     */
    private Integer pageNo;
    /**
     * 每页展示数据条数
     */
    private Integer pageSize;

}
