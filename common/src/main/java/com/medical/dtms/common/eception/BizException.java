/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.medical.dtms.common.eception;

import java.io.Serializable;

/**
 * 业务异常
 * @author shenqifeng
 * 为了解决feign 一直返回500的问题，这里的解决方案可以是定义多个BizException这种，名字不一样，然后使用
 * @ResponseStatus(value = HttpStatus.BAD_REQUEST) 定义value值，定义成我们需要的，然后再抛出异常的时候
 * ，使用不同的BizException ，即可解决
 * @version $Id: BizException.java, v 0.1 2019年3月11日 下午1:10:38 1 Exp $
 */
//@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BizException extends RuntimeException implements Serializable {


    /** serialVersionUID */
    private static final long serialVersionUID = -8303848557448027894L;

    private String            code;
    private String            message;

    public BizException() {
    }

    public BizException(String code, String message) {
        super();
        this.code = code;
        //这里的message使用|包装，格式是code|=|message
        this.message = code + "|" + message;
    }

    public String getCode() {
        return code;
    }

    @Override public String getMessage() {
        return message;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

