package com.medical.dtms.common.model.train;

import lombok.Data;

/**
 * @version: TrainExcelModel$.java v 1.0,2019$年09$月09$日15:00ruanqiuhan Exp$
 * @Descrption 培训管理 - 导出 model
 **/
@Data
public class TrainExcelModel {

    /**
     * 用户姓名
     */
    private String dspName;
    /**
     * 部门名称
     */
    private String deptName;
    /**
     * 培训名称
     */
    private String trainName;
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 得分/总分
     */
    private String point;
    /**
     * 完成时间 str
     */
    private String finishTimeStr;
    /**
     * 合格 不合格
     */
    private String passStr;
}
