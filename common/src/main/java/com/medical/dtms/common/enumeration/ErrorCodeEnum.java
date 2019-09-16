/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */

package com.medical.dtms.common.enumeration;

import org.apache.commons.lang.StringUtils;

/**
 * 错误枚举
 * 
 * @author shenqifeng
 * @version $Id: ErrorCodeEnum.java, v 0.1 2019年3月11日 下午1:13:59 1 Exp $
 */
public enum ErrorCodeEnum {

    SUCCESS("200", "操作成功"),

    FAILED("500", "操作失败"),

    NO_PERMISSION("405", "无权限"),

    PARAM_ERROR("40000", "参数异常"),

    PARAM_IS_EMPTY("40001", "参数为空"),

    PARAM_TOO_LONG("40002", "参数超长"),

    REMOTE_INVOCATION_ERROR("20000", "远程调用异常"),

    USER_AUTHORIZATION_ERROR("20001", "授权异常"),

    TOKEN_INVALID("10000", "token无效"),

    USER_NOT_LOGIN("10001", "用户未登录"),

    UNKNOWN_ERROR("99999", "未知错误"),

    NO_DATA("2000", "无数据");

    private String errorCode;

    private String errorMessage;

    ErrorCodeEnum(String code, String message) {
        this.errorCode = code;
        this.errorMessage = message;
    }

    /**
     * 根据code获取message
     * 
     * @param code
     * @return
     */
    public static String getDescByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        for (ErrorCodeEnum object : ErrorCodeEnum.values()) {
            if (object.getErrorCode().equals(code)) {
                return object.getErrorMessage();
            }
        }
        return null;
    }

    /**
     * 根据code获取枚举对象
     * 
     * @param code
     * @return
     */
    public static ErrorCodeEnum getEnumObjByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        for (ErrorCodeEnum object : ErrorCodeEnum.values()) {
            if (object.getErrorCode().equals(code)) {
                return object;
            }
        }
        return null;
    }

    /**
     * Getter method for property <tt>errorCode</tt>.
     * 
     * @return property value of errorCode
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * Getter method for property <tt>errorMessage</tt>.
     * 
     * @return property value of errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }

}
