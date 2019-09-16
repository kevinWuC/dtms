package com.medical.dtms.dto.task.query;

import lombok.Data;

/**
 * $Id：RuTaskQuery.java v 1.0, 2019/8/8 16:21 wuxuelin Exp$
 *
 * @Description 任务查询 实体类
 **/
@Data
public class RuTaskQuery {

    /**
     * 当前页码
     */
    private Integer    pageNo;
    /**
     * 每页展示数据条数
     */
    private Integer    pageSize;
}
