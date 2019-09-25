package com.medical.dtms.common.login;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;
import com.medical.dtms.common.model.menu.QMSMenuModel;
import com.medical.dtms.common.model.user.QMSUserInRoleModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @version： OperatorInfo.java v 1.0, 2019年08月23日 10:04 wuxuelin Exp$
 * @Description 登陆人信息
 **/
@Data
public class OperatorInfo implements Serializable {
    private static final long serialVersionUID = -7056904799569970131L;
    /**
     * 用户主键
     */
    private Long bizId;
    /**
     * 用户id
     */
    private String userId;
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
     * 用户角色 list
     */
    private List<QMSUserInRoleModel> roleList;
    /**
     * 用户所用有的菜单 list
     */
    private List<QMSMenuModel> menuLists;
    /**
     * 用户所拥有的菜单  urls
     */
    private List<String> menuUrls;
}
