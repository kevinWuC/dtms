package com.medical.dtms.common.model.file;

import lombok.Data;

/**
 * @version： SimpleFileAttachmentModel.java v 1.0, 2019年09月23日 19:58 wuxuelin Exp$
 * @Description 文件附件model
 **/
@Data
public class SimpleFileAttachmentModel {

    /**
     * 文件 url
     */
    private String fileUrl;
    /**
     * 文件 name
     */
    private String fileName;
}
