package com.medical.dtms.web.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medical.dtms.common.eception.BizException;
import com.medical.dtms.common.eception.ExceptionInfo;
import com.medical.dtms.common.eception.InternalException;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: chengtianping
 * @Date 2019-03-13 15:58
 * 异常错误处理
 */
@Slf4j
@Configuration
public class ExceptionErrorDecoder implements ErrorDecoder {

    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Exception decode(String s, Response response) {
        try {
            if (response.body() != null) {
                String body = Util.toString(response.body().asReader());
                ExceptionInfo ei = this.objectMapper.readValue(body.getBytes("UTF-8"), ExceptionInfo.class);
                Class clazz = Class.forName(ei.getException());
                Object obj = clazz.newInstance();
                //如果有新增加的业务异常 在这里增加
                if (obj instanceof BizException) {
                    BizException bizException = new BizException();
                    bizException.setCode(String.valueOf(ei.getStatus()));
                    bizException.setMessage(ei.getMessage());
                    return bizException;
                }
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return new InternalException(ex.getMessage());
        }
        log.error("系统异常,请联系管理员");
        return new InternalException("系统异常,请联系管理员");
    }

}
