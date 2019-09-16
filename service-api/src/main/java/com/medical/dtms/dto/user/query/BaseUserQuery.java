package com.medical.dtms.dto.user.query;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;
import lombok.Data;

/**
 * @version： BaseUserQuery.java v 1.0, 2019年08月21日 12:16 wuxuelin Exp$
 * @Description 用户查询基础类
 **/
@Data
public class BaseUserQuery {
    /**
     * 用户业务主键
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long bizId;
    /**
     * 用户id
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long userId;
    /**
     * 角色id
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long roleId;
    /**
     * 部门id
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long deptId;
    /**
     * 岗位ID
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long jobId;
    /**
     * 用户账号/姓名
     */
    private String account;
    /**
     * 用户姓名
     */
    private String dspName;
    /**
     * 当前页码
     */
    private Integer pageNo;
    /**
     * 每页展示数据条数
     */
    private Integer pageSize;

}
