package com.medical.dtms.common.thread;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @version： LogTaskExecutor.java v 1.0, 2019年09月23日 19:37 wuxuelin Exp$
 * @Description  任务线程
 **/
@Configuration
public class LogTaskExecutor {

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 设置核心线程数
        executor.setCorePoolSize(5);
        // 设置最大线程数
        executor.setMaxPoolSize(10);
        // 设置队列容量
        executor.setQueueCapacity(20);
        // 设置默认线程名称
        executor.setThreadNamePrefix("logTask");
        return executor;
    }
}
