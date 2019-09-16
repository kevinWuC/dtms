package com.medical.dtms.common.model.dept;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @version： QMSDeptModel.java v 1.0, 2019年08月20日 10:12  Exp$
 * @Description 部门model
 **/
@Data
public class QMSDeptModel implements Serializable {

    private static final long serialVersionUID = -8958034110238015847L;
    /**
     * 业务主键
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long bizId;
    /**
     * 父节点主键
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long parentId;
    /**
     * 部门名称
     */
    private String deptName;
    /**
     * 部门代码
     */
    private String code;
    /**
     * 部门层次
     */
    private String deptLevel;
    /**
     * 允许有效
     */
    private Boolean allowEnable;
    /**
     * 允许冻结
     */
    private Boolean allowFreeze;
    /**
     * 允许删除
     */
    private Boolean allowDelete;
    /**
     * 排序码
     */
    private String sortCode;
    /**
     * 是否为末级 true 是 false 否
     */
    private Boolean lastOrNot;
    /**
     * 下一级 列表
     */
    private List<QMSDeptModel> list;
}
