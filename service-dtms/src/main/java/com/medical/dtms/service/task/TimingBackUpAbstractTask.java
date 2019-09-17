package com.medical.dtms.service.task;

import lombok.Data;

/**
 * @version： TimingBackUpAbstractTask.java v 1.0, 2019年09月17日 21:21 wuxuelin Exp$
 * @Description
 **/
@Data
public abstract class TimingBackUpAbstractTask {

    private Long taskId;

    private String taskName;

    private String cron;

    private String creator;

    private String creatorId;

    private String urlPath;

    public TimingBackUpAbstractTask(Long taskId, String taskName, String cron, String creator, String creatorId, String urlPath) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.cron = cron;
        this.creator = creator;
        this.creatorId = creatorId;
        this.urlPath = urlPath;
    }

    public abstract void run(TimingBackUpAbstractTask task);

}
