package com.medical.dtms.common.enumeration.file;

import org.apache.commons.lang.StringUtils;

/**
 * @version： FileApplyTypeEnum.java v 1.0, 2019年08月27日 14:57 wuxuelin Exp$
 * @Description 文件报批类型枚举类
 **/
public enum FileApplyTypeEnum {

    FileApplyTypeEnum_1(1, "文件新增"),
    FileApplyTypeEnum_2(2, "文件修改"),
    FileApplyTypeEnum_3(3, "文件归档"),
    FileApplyTypeEnum_4(4, "未知"),
    FileApplyTypeEnum_5(5, "文件作废"),
    FileApplyTypeEnum_6(6, "修改");

    private Integer type;

    private String name;

    FileApplyTypeEnum(Integer type, String name) {
        this.type = type;
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 根据 name 获取 type
     *
     * @param code
     * @return
     */
    public static Integer getTypeByName(String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        }
        for (FileApplyTypeEnum object : FileApplyTypeEnum.values()) {
            if (StringUtils.equals(name, object.getName())) {
                return object.getType();
            }
        }
        return null;
    }

    /**
     * 根据 type 获取 name
     *
     * @param type
     * @return
     */
    public static String getNameByType(Integer type) {
        if (null == type) {
            return null;
        }
        for (FileApplyTypeEnum object : FileApplyTypeEnum.values()) {
            if (type.equals(object.type)) {
                return object.getName();
            }
        }
        return null;
    }
}
