package com.medical.dtms.common.model.file;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @version： FileAttachmentModel.java v 1.0, 2019年09月09日 17:08 wuxuelin Exp$
 * @Description 文件附件model
 **/
@Data
public class FileAttachmentModel {

    /**
     * 文件附件 名称
     */
    private String attachmentName;
    /**
     * 文件附件 格式
     */
    private String attachmentFormat;
    /**
     * 上传时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date upLoadTime;

}
