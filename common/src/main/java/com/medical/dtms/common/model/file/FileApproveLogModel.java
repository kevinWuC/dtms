package com.medical.dtms.common.model.file;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;
import lombok.Data;

import java.util.Date;

/**
 * @version： FileApproveLogModel.java v 1.0, 2019年08月28日 10:00 wuxuelin Exp$
 * @Description 流程记录 model
 **/
@Data
public class FileApproveLogModel {
    /**
     * 业务主键
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long bizId;
    /**
     * 文件主键
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long fileId;
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
     * 到达时间
     */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date beginDate;
    /**
     * 完成时间
     */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date finshedDate;
    /**
     * 处理人
     */
    private String approveUserName;
    /**
     * 结果 1 提交 2 待处理 3 拒绝 4 ok
     */
    private Integer result;
    /**
     * 结果名称
     */
    private String resultName;
    /**
     * 意见
     */
    private String remark;
}
