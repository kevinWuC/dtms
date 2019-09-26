package com.medical.dtms.common.model.syslog;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;
import lombok.Data;

import java.util.Date;

/**
 * @version： SysLoginLogModel.java v 1.0, 2019年08月21日 15:08 huangshuaiquan Exp$
 * @Description
 **/
@Data
public class SysLoginLogModel {

    private Long id;
    /**
     * 发生时间
     */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;
    /**
     * 登录账户
     */
    private String account;
    /**
     * 登录状态 true-登陆成功 false-登陆失败
     */
    private Boolean loginLogStatus;
    /**
     * 登录IP地址
     */
    private String ipAddress;
    /**
     * IP地址所在地址
     */
    private String ipAddressName;

}
