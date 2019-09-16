package com.medical.dtms.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @version： BaseDTO.java v 1.0, 2019年08月23日 15:46 wuxuelin Exp$
 * @Description 基础DTO
 **/
@Data
public class BaseDTO implements Serializable {

    private static final long serialVersionUID = -8507547547603187217L;
    /**
     * false-未删除  true-删除
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
