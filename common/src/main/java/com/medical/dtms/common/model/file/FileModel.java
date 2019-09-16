package com.medical.dtms.common.model.file;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;
import lombok.Data;

import java.util.Date;

/**
 * @version： FileModel.java v 1.0, 2019年08月27日 20:45 wuxuelin Exp$
 * @Description 文件报批 model
 **/
@Data
public class FileModel {
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
     * 申请部门 name
     */
    private String applyDeptName;
    /**
     * 申请人 name
     */
    private String applyUserName;
    /**
     * 申请日期
     */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date applyDate;
    /**
     * 文件状态 1 未提交 2 已提交 3 生效 4 归档 5 作废 6 退回 7 待审批
     */
    private Integer fileStatus;
    /**
     * 文件状态
     */
    private String fileStatusName;
    /**
     * 文件报批类型 1 文件新增 2 文件修改 3 文件归档 4 未知
     */
    private Integer fileApplyType;
    /**
     * 报批类型
     */
    private String fileApplyTypeName;
}
