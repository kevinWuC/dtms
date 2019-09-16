package com.medical.dtms.common.resp;

import com.medical.dtms.common.enumeration.ErrorCodeEnum;

/**
 * @author : Hejinsheng
 * @date Date : 2018年10月28日 16:01
 * @Description:
 */
public class Result<T> {

    private String code;

    private Boolean success;

    private String message;

    private T data;

    public Result() {
    }

    public Result(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result(String code, Boolean success, String message, T data) {
        this.code = code;
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public Result(String code, Boolean success, String message) {
        this.code = code;
        this.success = success;
        this.message = message;
    }

    public static Result buildSuccess() {
        return new Result<>(ErrorCodeEnum.SUCCESS.getErrorCode(), true, ErrorCodeEnum.SUCCESS.getErrorMessage());
    }

    public static Result buildSuccess(Object data) {
        return new Result<>(ErrorCodeEnum.SUCCESS.getErrorCode(), true, ErrorCodeEnum.SUCCESS.getErrorMessage(), data);
    }

    public static Result buildSuccess(String code, Boolean b, String message, Object data) {
        return new Result<>(code, b, message, data);
    }

    public static Result buildSuccess(String code, Boolean b, String message) {
        return new Result<>(code, b, message);
    }

    public static Result buildFailed() {
        return new Result(ErrorCodeEnum.FAILED.getErrorCode(), false, ErrorCodeEnum.FAILED.getErrorMessage());
    }

    public static Result buildFailed(ErrorCodeEnum errorCodeEnum) {
        return new Result(errorCodeEnum.getErrorCode(), false, errorCodeEnum.getErrorMessage());
    }

    public static Result buildFailed(String errCode, String erroMsg) {
        return new Result(errCode, false, erroMsg);
    }

    public static Result buildFailed(Object data) {
        return new Result<>(ErrorCodeEnum.FAILED.getErrorCode(), false, ErrorCodeEnum.FAILED.getErrorMessage(), data);
    }

    public static Result buildFailed(String code, Boolean b, String message, Object data) {
        return new Result<>(code, b, message, data);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
