package com.medical.dtms.dto.task;

import lombok.Data;


/**
 * $Id：RuTaskDTO.java v 1.0, 2019/8/8 15:49 wuxuelin Exp$
 *
 * @Description
 **/
@Data
public class RuTaskDTO {

    /**
     * 主键id
     */
    private String taskId;
    /**
     * 流程实例 id
     */
    private String procInstId;
    /**
     * 流程定义 id
     */
    private String procDefId;
    /**
     * 流程名称
     */
    private String name;
    /**
     * 父流程 id
     */
    private String parentTaskId;
    /**
     * 任务参与者
     */
    private String assignee;
}
