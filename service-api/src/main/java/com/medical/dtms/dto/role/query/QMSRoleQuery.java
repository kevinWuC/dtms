package com.medical.dtms.dto.role.query;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;
import lombok.Data;

/**
 * @version： QMSRoleQuery.java v 1.0, 2019年08月20日 12:09 wuxuelin Exp$
 * @Description 角色 查询类
 **/
@Data
public class QMSRoleQuery {
    /**
     * 业务主键
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long bizId;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 当前页码
     */
    private Integer pageNo;
    /**
     * 每页展示数据条数
     */
    private Integer pageSize;

    public QMSRoleQuery(String roleName) {
        this.roleName = roleName;
    }

    public QMSRoleQuery(Long bizId) {
        this.bizId = bizId;
    }

    public QMSRoleQuery() {
    }
}
