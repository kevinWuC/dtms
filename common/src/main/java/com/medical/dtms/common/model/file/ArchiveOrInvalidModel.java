package com.medical.dtms.common.model.file;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;
import lombok.Data;

import java.util.Date;

/**
 * @version： ArchiveOrInvalidModel.java v 1.0, 2019年08月31日 10:02 wuxuelin Exp$
 * @Description 归档/作废文件 model
 **/
@Data
public class ArchiveOrInvalidModel {

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
     * 版本号
     */
    private String fileVersion;
    /**
     * 生效日期
     */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date effectDate;
    /**
     * 编写部门 name
     */
    private String applyDeptName;
    /**
     * 操作时间
     */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date operatorDate;
    /**
     * 操作人
     */
    private String operatorName;

}
