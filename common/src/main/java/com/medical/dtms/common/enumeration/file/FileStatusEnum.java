package com.medical.dtms.common.enumeration.file;

import org.apache.commons.lang.StringUtils;

/**
 * @version： FileStatusEnum.java v 1.0, 2019年08月27日 14:49 wuxuelin Exp$
 * @Description 文件状态枚举类
 **/
public enum FileStatusEnum {

    FILE_STATUS_ENUM_1(1, "未提交"),
    FILE_STATUS_ENUM_2(2, "已提交"),
    FILE_STATUS_ENUM_3(3, "生效"),
    FILE_STATUS_ENUM_4(4, "归档"),
    FILE_STATUS_ENUM_5(5, "作废"),
    FILE_STATUS_ENUM_6(6, "退回"),
    FILE_STATUS_ENUM_7(7, "待审核"),
    FILE_STATUS_ENUM_8(8, "待审批");

    private Integer status;

    private String name;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    FileStatusEnum(Integer status, String name) {
        this.status = status;
        this.name = name;
    }

    /**
     * 根据 name 获取 status
     *
     * @param code
     * @return
     */
    public static Integer getStatusByName(String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        }
        for (FileStatusEnum object : FileStatusEnum.values()) {
            if (StringUtils.equals(name, object.getName())) {
                return object.getStatus();
            }
        }
        return null;
    }

    /**
     * 根据 status 获取 name
     *
     * @param type
     * @return
     */
    public static String getNameByStatus(Integer status) {
        if (null == status) {
            return null;
        }
        for (FileStatusEnum object : FileStatusEnum.values()) {
            if (status.equals(object.status)) {
                return object.getName();
            }
        }
        return null;
    }
}
