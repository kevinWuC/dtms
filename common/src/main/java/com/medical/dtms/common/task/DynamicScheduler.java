package com.medical.dtms.common.task;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

import com.medical.dtms.common.task.DemoTask;

import java.util.Arrays;
import java.util.List;

/**
 * @author Hejinsheng
 * 动态cron 定时任务示例
 */
//@Configuration
//@EnableScheduling
@Slf4j
public class DynamicScheduler implements SchedulingConfigurer {

    /**
     * 测试数据，实际可从数据库获取
     */
    List<DemoTask> tasks = Arrays.asList(
            new DemoTask(1, "任务1", "*/30 * * * * *"),
            new DemoTask(2, "任务2", "*/30 * * * * *"),
            new DemoTask(3, "任务3", "*/30 * * * * *"),
            new DemoTask(4, "任务4", "*/30 * * * * *"),
            new DemoTask(5, "任务5", "*/30 * * * * *")
    );

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        tasks.forEach(task -> {
            //任务执行线程
            Runnable runnable = () -> task.run();

            //任务触发器
            Trigger trigger = triggerContext -> {
                // 真实业务从数据库中获取变更 TODO
                if(task.getTaskId()==1){
                    task.setCron("*/5 * * * * *");
                }
                //获取定时触发器，这里可以每次从数据库获取最新记录，更新触发器，实现定时间隔的动态调整
                CronTrigger cronTrigger = new CronTrigger(task.getCron());
                return cronTrigger.nextExecutionTime(triggerContext);
            };

            //注册任务
            scheduledTaskRegistrar.addTriggerTask(runnable, trigger);
        });

    }
}
