package com.medical.dtms.common.model.user;

import lombok.Data;

/**
 * @version： SimpleUserModel.java v 1.0, 2019年08月27日 17:11 wuxuelin Exp$
 * @Description 简易 用户model
 **/
@Data
public class SimpleUserModel {
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 用户名
     */
    private String userName;
}
