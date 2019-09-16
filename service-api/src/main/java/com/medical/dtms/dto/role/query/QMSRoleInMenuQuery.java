package com.medical.dtms.dto.role.query;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;
import lombok.Data;

/**
 * @version： QMSRoleInMenuQuery.java v 1.0, 2019年08月20日 15:22 wuxuelin Exp$
 * @Description 角色 - 菜单关联查询实体类
 **/
@Data
public class QMSRoleInMenuQuery {

    /**
     * 角色id
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long roleId;
    /**
     * 菜单id
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long menuId;
}
