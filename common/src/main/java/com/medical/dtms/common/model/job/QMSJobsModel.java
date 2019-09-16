package com.medical.dtms.common.model.job;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;
import lombok.Data;

/**
 * @version： QMSJobsModel.java v 1.0, 2019年08月23日 13:30 huangshuaiquan Exp$
 * @Description 岗位设置 model
 **/
@Data
public class QMSJobsModel {

    /**
     * 业务主键
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long bizId;
    /**
     * 职位名称
     */
    private String jobName;
    /**
     * 允许查询 true-是 false-否
     */
    private Boolean allowQuery;
    /**
     * 允许只读 true-是 false-否
     */
    private Boolean allowRead;
    /**
     * 允许编辑 true-是 false-否
     */
    private Boolean allowEdit;
    /**
     * 允许启用 true-是 false-否
     */
    private Boolean allowEnable;
    /**
     * 允许冻结 true-是 false-否
     */
    private Boolean allowFreeze;
    /**
     * 允许删除 true-是 false-否
     */
    private Boolean allowDelete;
    /**
     * 排序码
     */
    private String sortCode;
    /**
     * 部门id
     */
    private String deptId;
    /**
     * 部门名称
     */
    private String deptName;

}
