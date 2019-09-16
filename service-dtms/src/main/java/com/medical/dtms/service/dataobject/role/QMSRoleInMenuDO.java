package com.medical.dtms.service.dataobject.role;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;
import lombok.Data;

import javax.persistence.Table;
import java.util.Date;
import java.util.List;

/**
 * @version： QMSRoleInMenuDO.java v 1.0, 2019年08月14日 10:52 wuxuelin Exp$
 * @Description  角色与菜单关系
 **/
@Data
@Table(name = "tb_dtms_qms_role_in_menu")
public class QMSRoleInMenuDO {
    /**
     * 自增id
     */
    private Long id;
    /**
     * 业务主键
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long bizId;
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
    /**
     * 菜单id 集合
     */
    private List<Long> menuIdList;
    /**
     * true - 未删除  false - 删除
     */
    private Boolean isDeleted;
    /**
     * 创建人
     */
    private String creator;
    /**
     * 创建人id
     */
    private String creatorId;
    /**
     * 创建时间
     */
    private Date gmtCreated;
    /**
     * 修改人
     */
    private String modifier;
    /**
     * 修改人id
     */
    private String modifierId;
    /**
     * 修改时间
     */
    private Date gmtModified;
}