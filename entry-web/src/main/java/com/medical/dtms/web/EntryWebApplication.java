package com.medical.dtms.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

/**
 * @author : Hejinsheng
 * @date Date : 2018年10月28日 16:01
 * @Description:
 */
@SpringBootApplication(scanBasePackages = {"com.medical.dtms.common", "com.medical.dtms.web"})
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.medical.dtms.feignservice"})
@EnableCircuitBreaker
@EnableHystrix
public class EntryWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(EntryWebApplication.class, args);
    }

}
