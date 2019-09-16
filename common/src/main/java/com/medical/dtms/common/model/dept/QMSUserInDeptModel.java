package com.medical.dtms.common.model.dept;

import lombok.Data;

/**
 * @version: QMSUserInDeptModel$.java v 1.0,2019$年09$月05$日15:28ruanqiuhan Exp$
 * @Descrption 部门人员关联 model
 **/
@Data
public class QMSUserInDeptModel {

    /**
     * 用户id
     */
    private Long userId;
    /**
     * 部门id
     */
    private Long deptId;
    /**
     * 部门 name
     */
    private String deptName;
}
