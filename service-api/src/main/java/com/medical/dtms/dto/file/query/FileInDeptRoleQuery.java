package com.medical.dtms.dto.file.query;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;
import lombok.Data;

import java.util.List;

/**
 * @version： FileInDeptRoleQuery.java v 1.0, 2019年08月28日 11:41 wuxuelin Exp$
 * @Description 文件查询模块 查询实体类
 **/
@Data
public class FileInDeptRoleQuery {

    /**
     * 文件id
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long fileId;
    /**
     * 角色id 集合
     */
    private List<Long> roleIdList;
    /**
     * 部门id集合
     */
    private List<Long> deptIdList;
}
