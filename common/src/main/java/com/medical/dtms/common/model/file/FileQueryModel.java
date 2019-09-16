package com.medical.dtms.common.model.file;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;
import lombok.Data;

import java.util.Date;

/**
 * @version： FileQueryModel.java v 1.0, 2019年08月28日 18:09 wuxuelin Exp$
 * @Description 文件查询 model
 **/
@Data
public class FileQueryModel {
    /**
     * 业务主键
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long bizId;
    /**
     * 文件编号
     */
    private String fileNo;
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 附件
     */
    private String fileContent;
    /**
     * 版本号
     */
    private String fileVersion;
    /**
     * 生效日期
     */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date effectDate;
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
