package com.medical.dtms.common.task;

import lombok.Data;

@Data
public abstract class AbstractTask {

    private int taskId;

    private String taskName;

    private String cron;

    public AbstractTask(int taskId, String taskName, String cron) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.cron = cron;
    }
//... 其他参数

    public abstract void run();
}
