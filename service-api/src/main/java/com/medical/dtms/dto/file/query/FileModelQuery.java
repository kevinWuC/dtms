package com.medical.dtms.dto.file.query;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;
import lombok.Data;

import java.util.List;

/**
 * @version： FileModelQuery.java v 1.0, 2019年08月27日 11:12 wuxuelin Exp$
 * @Description 文件查询实体类
 **/
@Data
public class FileModelQuery {
    /**
     * 业务主键（文件id）
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
     * 文件类别id
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long fileTypeId;
    /**
     * 申请部门（编制部门）ID
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long applyDeptId;
    /**
     * 编写人 id
     */
    private Long applyUserId;
    /**
     * 编写人
     */
    private String applyUser;
    /**
     * 审批人 id
     */
    private Long auditorId;
    /**
     * 会签人 1 集合
     */
    private String signor1Id;
    /**
     * 会签人2 集合
     */
    private String signator2Id;
    /**
     * 批准人集合
     */
    private String approverId;
    /**
     * 审批人name
     */
    private String auditorName;
    /**
     * 发布年度
     */
    private Integer applyYear;
    /**
     * 申请类别 id
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long applyType;
    /**
     * 编写人id
     */
    private String userId;
    /**
     * 角色id
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long roleId;
    /**
     * 接收部门id
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long receiveDeptId;
    /**
     * 文件 id 集合
     */
    private List<Long> fileIds;
    /**
     * 年审记录 id
     */
    private List<Long> yearApplyIds;
    /**
     * 只看未接收文件
     */
    private Boolean isReceived;
    /**
     * 文件状态
     */
    private Integer fileStatus;
    /**
     * 是否接收 true 是 false 否
     */
    private Boolean receivedNot;
    /**
     * 当前页码
     */
    private Integer pageNo;
    /**
     * 每页展示数据条数
     */
    private Integer pageSize;
}
