package com.medical.dtms.dto.item.query;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;
import lombok.Data;

/**
 * @version： QMSItemDetailsQuery.java v 1.0, 2019年08月16日 15:11 wuxuelin Exp$
 * @Description
 **/
@Data
public class QMSItemDetailsQuery {

    /**
     * 业务主键
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long bizId;
    /**
     * 代码
     */
    private String itemCode;
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
     * 当前页码
     */
    private Integer pageNo;
    /**
     * 每页展示数据条数
     */
    private Integer pageSize;

    public QMSItemDetailsQuery() {
    }

    public QMSItemDetailsQuery(String itemCode, Long itemsId) {
        this.itemCode = itemCode;
        this.itemsId = itemsId;
    }

    public QMSItemDetailsQuery(Long bizId) {
        this.bizId = bizId;
    }
}
