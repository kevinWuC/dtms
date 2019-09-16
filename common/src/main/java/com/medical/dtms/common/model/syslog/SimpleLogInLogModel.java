package com.medical.dtms.common.model.syslog;

import lombok.Data;

import java.util.Date;

/**
 * @version： SimpleLogInLogModel.java v 1.0, 2019年09月03日 19:06 wuxuelin Exp$
 * @Description 简易登陆人 model
 **/
@Data
public class SimpleLogInLogModel {

    /**
     * 用户id
     */
    private Long userId;
    /**
     * 最后一次登录时间
     */
    private Date lastVisit;
    /**
     * 登录次数
     */
    private Integer logOnCount;
}
