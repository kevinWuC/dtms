package com.medical.dtms.common.model.exam;

import lombok.Data;

/**
 * @version: TrainExcelModel$.java v 1.0,2019$年09$月09$日15:00ruanqiuhan Exp$
 * @Descrption 考试统计 - 导出 model
 **/
@Data
public class ExamExcelModel {

    /**
     * 用户姓名
     */
    private String dspName;
    /**
     * 部门名称
     */
    private String deptName;
    /**
     * 考试名称
     */
    private String examName;
    /**
     * 得分
     */
    private String pointStr;
    /**
     * 及格/总分
     */
    private String passTotal;
    /**
     * 完成时间 str
     */
    private String finishTimeStr;
    /**
     * 合格 不合格
     */
    private String passStr;
}
