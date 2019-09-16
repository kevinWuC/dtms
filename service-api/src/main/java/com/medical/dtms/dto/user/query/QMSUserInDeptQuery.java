package com.medical.dtms.dto.user.query;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;
import lombok.Data;

import java.util.List;

/**
 * @version： QMSRoleInMenuQuery.java v 1.0, 2019年08月20日 15:22 wuxuelin Exp$
 * @Description 角色 - 菜单关联查询实体类
 **/
@Data
public class QMSUserInDeptQuery {

    /**
     * 角色id
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long userId;
    /**
     * 部门id
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long deptId;
    /**
     *  用户id 集合
     * */
    private List<Long> userIds;
    /** 1 用户 2 部门*/
    private Integer userOrDept;
}
