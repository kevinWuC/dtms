package com.medical.dtms.dto.menu.query;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;
import lombok.Data;

/**
 * $Id：MenuQuery.java v 1.0, 2019/3/14 16:02 wuxuelin Exp$
 *
 * @Description
 **/
@Data
public class QMSMenuQuery {
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
     * 是否展开 true - 是 false - 否
     */
    private Boolean isUnfold;

    public QMSMenuQuery(Long parentId, String code) {
        this.parentId = parentId;
        this.code = code;
    }

    public QMSMenuQuery(Long bizId) {
        this.bizId = bizId;
    }

    public QMSMenuQuery() {
    }
}
