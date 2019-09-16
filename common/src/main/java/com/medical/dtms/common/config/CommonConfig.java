package com.medical.dtms.common.config;

import com.alibaba.fastjson.JSONObject;
import com.medical.dtms.common.interceptor.PermCheckInterceptor;
import com.medical.dtms.common.interceptor.RequestContextHystrixConcurrencyStrategy;
import com.medical.dtms.common.resp.Result;
import com.medical.dtms.common.resp.ResultCode;
import com.netflix.hystrix.strategy.HystrixPlugins;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

/**
 * @author : Hejinsheng
 * @date Date : 2018年10月29日 9:58
 * @Description:
 */
@Slf4j
//@Configuration
public class CommonConfig extends WebMvcConfigurerAdapter {


    /* MultipartResolver与servletFileupload冲突，不要同时使用
    * @Bean
    public MultipartResolver multipartResolver(){
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(100 * 1024 * 8);
        return multipartResolver;
    }*/

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        super.configureHandlerExceptionResolvers(exceptionResolvers);
        exceptionResolvers.add(new HandlerExceptionResolver() {
            @Override
            public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
                e.printStackTrace();
                Result result = Result.buildFailed();
                // 需要自定义处理的异常在此处添加分支
                if(e instanceof IllegalArgumentException){
                    result = Result.buildFailed(ResultCode.ILLEGAL_PARAM);
                } else if(e instanceof HttpRequestMethodNotSupportedException){
                    result = Result.buildFailed(ResultCode.FAILED);
                }else{
                    result.setCode("999");
                    result.setMessage("未知异常");
                }
                writeToResp(httpServletResponse,result);
                return null;
            }
        });
    }

    private void writeToResp(HttpServletResponse response, Result result){

        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        try(Writer writer = response.getWriter()) {
            writer.write(JSONObject.toJSONString(result));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        registry.addInterceptor(new PermCheckInterceptor());
    }

    /**
     * hystrixcommand标注的方法在子线程中运行，需要特殊处理
     */
    @PostConstruct
    public void init() {
        HystrixPlugins.getInstance().registerConcurrencyStrategy(new RequestContextHystrixConcurrencyStrategy());
    }
}
