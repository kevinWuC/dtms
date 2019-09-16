package com.medical.dtms.common.model.syslog;

import lombok.Data;

/**
 * @version： SimpleLogInLogModel.java v 1.0, 2019年09月03日 19:06 wuxuelin Exp$
 * @Description 简易 用户 - 角色关联 model
 **/
@Data
public class BaseSimpleUserModel {

    private Long userId;
    private Long roleId;
    private Long jobId;
    private Long deptId;
}
