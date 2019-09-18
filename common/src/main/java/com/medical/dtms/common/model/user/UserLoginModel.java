package com.medical.dtms.common.model.user;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;
import com.medical.dtms.common.model.menu.QMSMenuModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @version： UserLoginModel.java v 1.0, 2019年08月22日 18:27 wuxuelin Exp$
 * @Description 用户登录 model
 **/
@Data
public class UserLoginModel implements Serializable {
    private static final long serialVersionUID = 1008401744353585547L;
    /**
     * 用户主键
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long bizId;
    /**
     * 用户账号
     */
    private String account;
    /**
     * 用户姓名
     */
    private String dspName;
    /**
     * 部门ID（默认部门）
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long deptId;
    /**
     * 部门名称（默认部门）
     */
    private String deptName;
    /**
     * 是否保持登录 true 是 false 否
     */
    private Boolean keepLogin;
    /**
     * 用户角色 list
     */
    private List<QMSUserInRoleModel> roleList;
    /**
     * 用户所用有的菜单 list
     */
    private List<QMSMenuModel> menuLists;
}
