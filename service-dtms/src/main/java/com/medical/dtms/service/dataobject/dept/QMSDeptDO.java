package com.medical.dtms.service.dataobject.dept;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;
import lombok.Data;

import javax.persistence.Table;
import java.util.Date;

/**
 * @version： QMSDeptDO.java v 1.0, 2019年08月14日 10:52 wuxuelin Exp$
 * @Description  部门（专业组）
 **/
@Data
@Table(name = "tb_dtms_qms_dept")
public class QMSDeptDO {

    /**
     * 自增主键
     */
    private Long id;
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
     * 删除标记:0-正常，1-删除
     */
    private Boolean isDeleted;
    /**
     * 创建用户
     */
    private String creator;
    /**
     * 创建用户主键
     */
    private String creatorId;
    /**
     * 创建时间
     */
    private Date gmtCreated;
    /**
     * 修改用户
     */
    private String modifier;
    /**
     * 修改用户主键
     */
    private String modifierId;
    /**
     * 修改时间
     */
    private Date gmtModified;
}