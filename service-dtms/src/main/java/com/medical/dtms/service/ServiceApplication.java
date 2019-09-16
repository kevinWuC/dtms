package com.medical.dtms.service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.retry.annotation.EnableRetry;

/**
 * @author : Hejinsheng
 * @date Date : 2018年10月28日 16:01
 * @Description:
 */
@SpringBootApplication(scanBasePackages = { "com.medical.dtms.service",
                                            "com.medical.dtms.common" })
@EnableEurekaClient
@EnableCircuitBreaker
@MapperScan("com.medical.dtms.service.mapper")
@EnableRetry
public class ServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class, args);
    }
}
