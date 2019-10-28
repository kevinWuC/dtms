package com.medical.dtms.common.model.train.query;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;
import lombok.Data;

/**
 * @version： MyTrainPageQuery.java v 1.0, 2019年10月11日 16:20 wuxuelin Exp$
 * @Description 培训管理 - 我的培训 - 查询query
 **/
@Data
public class MyTrainPageQuery {


    /**
     * 当前页码
     */
    private Integer pageNo;
    /**
     * 每页展示数据条数
     */
    private Integer pageSize;
    /**
     * 用户id
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long userId;
}
