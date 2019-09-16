package com.medical.dtms.common.resp;

/**
 * @author : Hejinsheng
 * @date Date : 2018年10月28日 16:01
 * @Description:
 */
public enum  ResultCode {
    SUCCESS(200,"SUCCESS"),
    FAILED(500,"FAILED"),

    REQUEST_UNAUTH(401,"用户未认证"),
    ILLEGAL_PARAM(601,"参数不合法");

    private int code;

    private String desc;

    ResultCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
