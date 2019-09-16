package com.medical.dtms.dto.dept.query;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;
import lombok.Data;

/**
 * @version： QMSDeptQuery.java v 1.0, 2019年08月14日 16:52
 * @Description 部门设置 查询类
 **/
@Data
public class QMSDeptQuery {

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
    private String deptName;


    /**
     * @param parentId
     * @param code
     */
    public QMSDeptQuery(Long parentId, String code) {
        this.parentId = parentId;
        this.code = code;
    }

    public QMSDeptQuery(Long bizId) {
        this.bizId = bizId;
    }

    public QMSDeptQuery() {
    }
}
