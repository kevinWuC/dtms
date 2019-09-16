package com.medical.dtms.dto.log;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;
import lombok.Data;

import java.util.Date;

/**
 * @version： QMSSysLoginLogDTO.java v 1.0, 2019年08月14日 10:52 wuxuelin Exp$
 * @Description  登录系统日志
 **/
@Data
public class QMSSysLoginLogDTO {

    /**
     * 自增主键
     */
    private Long id;
    /**
     * 业务主键
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long bizId;
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
    /**
     * true-删除，false-正常
     */
    private Boolean isDeleted;
    /**
     * 创建用户
     */
    private String creator;
    /**
     * 创建用户主键
     */
    private Long creatorId;
    /**
     * 创建时间
     */
    private Date gmtCreated;
    /**
     * 修改用户
     */
    private String modifier;
    /**
     * 修改用户主键
     */
    private String modifierId;
    /**
     * 修改时间
     */
    private Date gmtModified;
}