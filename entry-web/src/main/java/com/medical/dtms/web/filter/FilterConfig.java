/**
 * BEYONDSOFT.COM INC
 */

package com.medical.dtms.web.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author chengtianping
 */
@Configuration
public class FilterConfig {

    @Bean
    public LoginFilter initProdIntercepter() {
        return new LoginFilter();
    }

    @Bean
    public FilterRegistrationBean configFilters() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(initProdIntercepter());
        registration.addUrlPatterns("/*");
        return registration;
    }

}
