package com.medical.dtms.common.model.file;

import lombok.Data;


/**
 * @version： ExportModel.java v 1.0, 2019年08月28日 21:14 wuxuelin Exp$
 * @Description 导出文件 model
 **/
@Data
public class ExportModel{
    /**
     * 文件编号
     */
    private String fileNo;
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 版本号
     */
    private String fileVersion;
    /**
     * 生效日期
     */
    private String effectDate;
    /**
     * 文件类别 name
     */
    private String fileTypeIdName;
    /**
     * 编写部门 name
     */
    private String applyDeptName;
    /**
     * 编写人 name
     */
    private String applyUserName;
    /**
     * 审核人 name
     */
    private String approverUserName;
    /**
     * 审批人 name
     */
    private String auditorName;
}
