package com.medical.dtms.common.model.dropdown;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;
import lombok.Data;

/**
 * @version： DropDownModel.java v 1.0, 2019年09月04日 11:09 wuxuelin Exp$
 * @Description 下拉model
 **/
@Data
public class DropDownModel {

    /**
     * 文件类别 id
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long fileTypeId;
    /**
     * 文件类别 name
     */
    private String fileTypeIdName;
    /**
     * 申请原因 id
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long applyReasonId;
    /**
     * 申请原因 name
     */
    private String applyReasonName;
    /**
     * 接收部门id
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long receiveDeptId;
    /**
     * 接收部门 name
     */
    private String receiveDeptName;
    /**
     * 申请部门id
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long applyDeptId;
    /**
     * 申请部门 name
     */
    private String applyDeptName;
    /**
     * 角色id
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long roleId;
    /**
     * 角色 name
     */
    private String roleName;
    /**
     * 发布年度
     */
    private Integer applyYear;
    /**
     * 申请类别 id
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long applyTypeId;
    /**
     * 申请类别 name
     */
    private String applyTypeName;
    /**
     * 人员id
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long userId;
    /**
     * 人员 name
     */
    private String userName;
}
