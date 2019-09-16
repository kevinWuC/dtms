package com.medical.dtms.common.model.style;

import lombok.Data;

/**
 * @version： StyleModel.java v 1.0, 2019年09月04日 14:44 wuxuelin Exp$
 * @Description 表格样式model
 **/
@Data
public class StyleModel {
    /**
     * 是否合并单元格 true 是 false 否
     */
    private Boolean merge;
    /**
     * 合并时 开始的第一行
     */
    private Integer firstRow;
    /**
     * 合并时 结束的第一行
     */
    private Integer lastRow;
    /**
     * 合并时 开始的第一列
     */
    private Integer firstCol;
    /**
     * 合并时 结束的第一列
     */
    private Integer lastCol;
    /**
     * 标题在第几行
     */
    private Integer titleRow;
    /**
     * 表头在第几行
     */
    private Integer headRow;
    /**
     * 正文从第几行开始
     */
    private Integer bodyRow;
}
