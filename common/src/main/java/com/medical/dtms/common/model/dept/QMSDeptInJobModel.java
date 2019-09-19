package com.medical.dtms.common.model.dept;

import com.medical.dtms.common.model.job.SimpleQMSJobsModel;
import lombok.Data;

import java.util.List;

/**
 * @version： QMSDeptInJobModel.java v 1.0, 2019年09月19日 10:39 wuxuelin Exp$
 * @Description 部门 - 职位 关联 model
 **/
@Data
public class QMSDeptInJobModel {
    /**
     * 部门 id
     */
    private Long bizId;
    /**
     * 部门名称
     */
    private String deptName;
    /**
     * 部门关联的岗位集合
     */
    private List<SimpleQMSJobsModel> jobsModels;
}
