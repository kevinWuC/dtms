package com.medical.dtms.web.filter;

import com.medical.dtms.common.constants.Constants;
import com.medical.dtms.common.login.OperatorInfo;
import com.medical.dtms.common.login.SessionConstants;
import com.medical.dtms.common.login.SessionTools;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import javax.servlet.FilterConfig;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @version： LoginFilter.java v 1.0, 2019年08月23日 12:26 wuxuelin Exp$
 * @Description
 **/
@Slf4j
public class LoginFilter implements Filter {

    // 设置白名单url
    private static List<String> whiteUrls = new ArrayList<>();

    @Autowired
    private Environment ev;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        whiteUrls.add("/");
        whiteUrls.add("/user/login");
        whiteUrls.add("/user/logout");
        whiteUrls.add("/syslog/addSysLog");
        whiteUrls.add("/file/uploadFiles");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String url = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());

        // 不拦截静态资源
        if (url.lastIndexOf(".") > 0) {
            chain.doFilter(httpRequest, httpResponse);
            return;
        }

        // 过滤不需要拦截的请求
        if (whiteUrls.contains(url)) {
            chain.doFilter(httpRequest, httpResponse);
            return;
        }

        // 校验是否登录
        OperatorInfo operator = SessionTools.getOperator();
        if (null == operator) {
            log.info("用户未登录，跳转到登录页");
            httpResponse.sendRedirect(ev.getProperty(SessionConstants.LOGIN_URL) + Constants.LOGIN);
            return;
        }

        // 获取请求的菜单 url
        String menuUrl = httpRequest.getHeader("menuUrl");
        if (StringUtils.isBlank(menuUrl)) {
            menuUrl = url;
        }

        // 获取请求菜单的 前两级
        String[] menuUrls = menuUrl.split("/");
        menuUrl = "";
        for (int i = 0, j = 0, len = menuUrls.length; i < len && j < 2; i++) {
            if (StringUtils.isNotBlank(menuUrls[i])) {
                menuUrl += "/" + menuUrls[i];
                j++;
            }
        }

        // 校验有无菜单权限
        List<String> models = operator.getMenuUrls();
        if (CollectionUtils.isEmpty(models) || !models.contains(menuUrl) && !StringUtils.equals(menuUrl, Constants.NO_PERMISSION)) {
            log.info("该用户无菜单权限,用户名:" + operator.getDspName());
            httpResponse.sendRedirect(ev.getProperty(SessionConstants.LOGIN_URL) + Constants.NO_PERMISSION);
            return;
        }

        chain.doFilter(httpRequest, httpResponse);
        return;
    }

    @Override
    public void destroy() {

    }
}
