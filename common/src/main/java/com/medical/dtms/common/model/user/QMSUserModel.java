package com.medical.dtms.common.model.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @version： QMSUserModel.java v 1.0, 2019年08月20日 18:10 wuxuelin Exp$
 * @Description 用户model
 **/
@Data
public class QMSUserModel {

    /**
     * 业务主键
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
     * 部门ID
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long deptId;
    /**
     * 部门名称
     */
    private String deptName;
    /**
     * 角色ID
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long roleId;
    /**
     * 角色名称
     */
    private String roleName;
//    /**
//     * 密码
//     */
//    private String passWord;
    /**
     * 是否启用 true 是 false 否
     */
    private Boolean allowEnable;
    /**
     * 排序码
     */
    private String sortCode;
    /**
     * 登录次数
     */
    private Integer logOnCount;
    /**
     * 前一次访问时间
     */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date previousVisit;
    /**
     * 最后一次访问时间
     */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date lastVisit;
    /**
     * 电话
     */
    private String mobile;
    /**
     * 邮箱
     */
    private String email;
    /**
     * QQ
     */
    private String qq;
    /**
     * 微信
     */
    private String wx;
    /**
     * 角色id 集合
     */
    private List<String> roleIdListStr;
    /**
     * 岗位ID 集合
     */
    private List<String> jobIdListStr;
    /**
     * 部门id 集合
     */
    private List<String> deptIdListStr;
}
