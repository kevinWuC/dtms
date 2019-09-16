package com.medical.dtms.dto.file;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @version： FileApproveLogDTO.java v 1.0, 2019年08月27日 10:45 wuxuelin Exp$
 * @Description 审批记录 实体类
 **/
@Data
public class FileApproveLogDTO {

    /**
     * 自增id
     */
    private Long id;
    /**
     * 业务主键
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long bizId;
    /**
     * id 集合
     */
    private List<Long> bizIdList;
    /**
     * 文件id
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long fileId;
    /**
     * 文件编号
     */
    private String fileNo;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件版本
     */
    private String fileVersion;
    /**
     * 到达时间
     */
    private Date beginDate;
    /**
     * 完成时间
     */
    private Date finshedDate;
    /**
     * 处理人id
     */
    private String approveUserId;
    /**
     * 处理人
     */
    private String approveUserName;
    /**
     * 结果 1 同意 2 退回 3 待处理 4 ok
     */
    private Integer result;
    /**
     * 意见
     */
    private String remark;
    /**
     * 记录类型 1新增记录 2 修改记录
     */
    private Integer operatorType;
    /**
     * 0-未删除  1-删除
     */
    private Boolean isDeleted;
    /**
     * 创建人
     */
    private String creator;
    /**
     * 创建人id
     */
    private String creatorId;
    /**
     * 创建时间
     */
    private Date gmtCreated;
    /**
     * 修改人
     */
    private String modifier;
    /**
     * 修改人id
     */
    private String modifierId;
    /**
     * 修改时间
     */
    private Date gmtModified;
}
