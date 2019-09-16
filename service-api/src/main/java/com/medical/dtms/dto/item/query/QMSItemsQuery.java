package com.medical.dtms.dto.item.query;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;
import lombok.Data;

/**
 * @version： QMSItemsQuery.java v 1.0, 2019年08月14日 16:52 wuxuelin Exp$
 * @Description 数据字典 查询类
 **/
@Data
public class QMSItemsQuery {

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
}
