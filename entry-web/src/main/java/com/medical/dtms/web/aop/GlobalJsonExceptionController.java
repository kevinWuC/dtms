package com.medical.dtms.web.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.medical.dtms.common.eception.BizException;
import com.medical.dtms.common.enumeration.ErrorCodeEnum;
import com.medical.dtms.common.resp.Result;

/**
 * @author chengtianping
 * @description 全局统一处理异常
 * @date 2017/8/30
 */
@RestControllerAdvice({ "com.medical.dtms.web.controller" })
public class GlobalJsonExceptionController {

    private static final Logger LOGGER = LoggerFactory
        .getLogger(GlobalJsonExceptionController.class);

    /**
     * ResponseBody 的controller 统一处理异常 自定义异常
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = {Exception.class})
    public Result exception(Exception ex) {
        Result result;
        // 需要自定义处理的异常在此处添加分支
        if (ex instanceof IllegalArgumentException) {
            result = Result.buildFailed(ErrorCodeEnum.PARAM_ERROR.getErrorCode(),
                ErrorCodeEnum.PARAM_ERROR.getErrorMessage());
        } else if (ex instanceof BizException) {
            //这里的message使用|包装，格式是code|=|message
            String code = ex.getMessage().substring(0, ex.getMessage().indexOf("|"));
            String message = ex.getMessage().substring(ex.getMessage().indexOf("|") + 1);
            result = Result.buildFailed(code, message);
        } else {
            result = Result.buildFailed(ErrorCodeEnum.UNKNOWN_ERROR.getErrorCode(),
                ErrorCodeEnum.UNKNOWN_ERROR.getErrorMessage());
        }
        LOGGER.error(ex.getMessage());
        return result;
    }

//    @ResponseBody
//    @ExceptionHandler(value = BizException.class)
//    public Result HandBizException(BizException ex) {
//        Result result;
//        // 需要自定义处理的异常在此处添加分支
//        if (ex instanceof IllegalArgumentException) {
//            result = Result.buildFailed(ErrorCodeEnum.PARAM_ERROR.getErrorCode(),
//                    ErrorCodeEnum.PARAM_ERROR.getErrorMessage());
//        } else if (ex instanceof BizException) {
//            //这里的message使用|包装，格式是code|=|message
//            String code = ex.getMessage().substring(0, ex.getMessage().indexOf("|"));
//            String message = ex.getMessage().substring(ex.getMessage().indexOf("|") + 1);
//            result = Result.buildFailed(code, message);
//        } else {
//            result = Result.buildFailed(ErrorCodeEnum.UNKNOWN_ERROR.getErrorCode(),
//                    ErrorCodeEnum.UNKNOWN_ERROR.getErrorMessage());
//        }
//        LOGGER.error(ex.getMessage());
//        return result;
//    }

}
