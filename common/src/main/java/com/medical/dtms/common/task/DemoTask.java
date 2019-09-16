package com.medical.dtms.common.task;

import lombok.Data;

/**
 * 示例任务
 */
@Data
public class DemoTask extends AbstractTask{
    public DemoTask(int taskId, String taskName, String cron) {
        super(taskId, taskName, cron);
    }

    @Override
    public void run() {
        // do someting
        System.out.println("taskId="+getTaskId()+",taskName="+getTaskName());
    }
}
