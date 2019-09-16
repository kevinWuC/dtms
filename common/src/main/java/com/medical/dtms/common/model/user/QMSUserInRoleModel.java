package com.medical.dtms.common.model.user;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;
import lombok.Data;

import java.io.Serializable;

/**
 * @version： QMSUserInRoleModel.java v 1.0, 2019年08月23日 10:38 wuxuelin Exp$
 * @Description 用户角色 关联 model
 **/
@Data
public class QMSUserInRoleModel implements Serializable {

    private static final long serialVersionUID = -9214958411164414407L;
    /**
     * 角色id
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long roleId;
    /**
     * 角色名称
     */
    private String roleName;
}
