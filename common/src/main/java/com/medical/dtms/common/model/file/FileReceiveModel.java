package com.medical.dtms.common.model.file;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;
import lombok.Data;

import java.util.Date;

/**
 * @version： FileReceiveModel.java v 1.0, 2019年08月30日 9:55 wuxuelin Exp$
 * @Description 文件接收 model
 **/
@Data
public class FileReceiveModel {
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
     * 编写部门 name
     */
    private String applyDeptName;
    /**
     * 接收部门 name
     */
    private String receiveDeptName;
    /**
     * 附件
     */
    private String fileContent;
    /**
     * 是否接收 false 否 true 是
     */
    private Boolean isReceived;
    /**
     * 接收时间
     */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date receiveDate;
    /**
     * 接收人 name
     */
    private String receiverName;
}
