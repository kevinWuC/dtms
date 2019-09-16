package com.medical.dtms.common.model.train;

import lombok.Data;

/**
 * @version: SimpleTrainFileModel$.java v 1.0,2019$年09$月09$日11:45ruanqiuhan Exp$
 * @Descrption
 **/
@Data
public class SimpleTrainFileModel {
    /**
     * 培训 id
     */
    private Long trainId;
    /**
     * 培训文件 name
     */
    private String fileName;
}
