package com.medical.dtms.service.task;

import com.medical.dtms.service.manager.database.QMSTaskManager;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

import java.util.List;

/**
 * @version： TimingBackUpDataBaseSchdule.java v 1.0, 2019年09月17日 11:04 wuxuelin Exp$
 * @Description 定时任务
 **/
@EnableScheduling
@Configuration
public class TimingBackUpDataBaseSchdule implements SchedulingConfigurer {

    @Autowired
    private QMSTaskManager taskManager;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        List<TimingBackUpTask> taskList = taskManager.listTasks();
        if (CollectionUtils.isNotEmpty(taskList)) {
            //任务执行线程
            taskList.forEach(task -> {
                        Runnable runnable = () -> task.run(task);
                        //任务触发器
                        Trigger trigger = triggerContext -> {
                            //获取定时触发器，这里可以每次从数据库获取最新记录，更新触发器，实现定时间隔的动态调整
                            CronTrigger cronTrigger = new CronTrigger(task.getCron());
                            return cronTrigger.nextExecutionTime(triggerContext);
                        };

                        //注册任务
                        taskRegistrar.addTriggerTask(runnable, trigger);
                    }
            );
        }
    }
}
