package com.medical.dtms.common.model.file;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;
import lombok.Data;

import java.util.Date;

/**
 * @version： FileApplyModel.java v 1.0, 2019年08月29日 21:03 wuxuelin Exp$
 * @Description 修改申请 - 列表页（分页）
 **/
@Data
public class FileApplyModel {

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
     * 版本号
     */
    private String fileVersion;
    /**
     * 生效日期
     */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date effectDate;
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
    /**
     * 允许修改 true 是 false 否
     */
    private Boolean allowModify;
    /**
     * 允许作废 true 是 false 否
     */
    private Boolean allowBad;
    /**
     * 申请状态：锁定 1，结束变为0
     */
    private Integer applyStatus;
    /**
     * 文件状态 1 未提交 2 已提交 3 生效 4 归档 5 作废 6 退回 7 待审批
     */
    private Integer fileStatus;
}
