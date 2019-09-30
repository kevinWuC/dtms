package com.medical.dtms.common.model.syslog;

import lombok.Data;

/**
 * @version： SimpleLogInLogModel.java v 1.0, 2019年09月03日 19:06 wuxuelin Exp$
 * @Description 简易 用户 - 角色 - 岗位 - 部门关联 model
 **/
@Data
public class BaseSimpleUserModel {

    /** 用户id*/
    private Long userId;
    /** 角色 id*/
    private Long roleId;
    /** 角色名称*/
    private String roleName;
    /** 职位id*/
    private Long jobId;
    /** 职位名称 */
    private String jobName;
    /** 部门id */
    private Long deptId;
    /** 部门名称 */
    private String deptName;
}
