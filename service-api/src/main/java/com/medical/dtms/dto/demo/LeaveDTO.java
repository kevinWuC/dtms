package com.medical.dtms.dto.demo;

import com.medical.dtms.dto.task.RuTaskDTO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * $Id：Leave.java v 1.0, 2019/8/7 19:07 wuxuelin Exp$
 *
 * @Description 请假实体类
 **/
@Data
public class LeaveDTO extends RuTaskDTO implements Serializable {

    private static final long serialVersionUID = 4624135128524120241L;
    /**
     * 自增id
     */
    private Long id;
    /**
     * 业务主键
     */
    private Long bizId;
    /**
     * 人员 id
     */
    private String userId;
    /**
     * 审批内容
     */
    private String content;
    /**
     * 审批人 id
     */
    private String approveId;
    /**
     * 是否同意 0 不同意 1 同意
     */
    private Integer pass;
    /**
     * 是否删除 0 正常 1 删除
     */
    private Boolean isDeleted;
    /**
     * 创建人姓名
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
     * 修改人 姓名
     */
    private String modifier;
    /**
     * 修改人 id
     */
    private String modifierId;
    /**
     * 修改时间
     */
    private Date gmtModified;
}
