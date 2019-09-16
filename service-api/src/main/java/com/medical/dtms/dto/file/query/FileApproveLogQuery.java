package com.medical.dtms.dto.file.query;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;
import lombok.Data;

/**
 * @version： FileApproveLogQuery.java v 1.0, 2019年08月28日 10:05 wuxuelin Exp$
 * @Description 流程记录查询实体类
 **/
@Data
public class FileApproveLogQuery {
    /**
     * 文件主键
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long fileId;
    /**
     * 完成时间是否为空 true 是 false 否
     */
    private Boolean empty;
    /**
     * 处理人id
     */
    private String approveUserId;
    /**
     * 1 新增 2 修改
     */
    private Integer operatorType;
    /**
     * 当前页码
     */
    private Integer pageNo;
    /**
     * 每页展示数据条数
     */
    private Integer pageSize;
}
