package com.medical.dtms.common.enumeration.log;

import org.apache.commons.lang.StringUtils;

/**
 * 操作类型枚举类
 */
public enum OperationTypeEnum {
    OPERATION_TYPE_INSERT(1,"新增"),
    OPERATION_TYPE_UPDATE(2,"修改"),
    OPERATION_TYPE_DELETE(3,"删除"),
    OPERATION_TYPE_OTHER(4,"其他");

    private Integer type;

    private String name;


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

    OperationTypeEnum(Integer type, String name) {
        this.type = type;
        this.name = name;
    }


    /**
     * 根据name获取type
     *
     * @param code
     * @return
     */
    public static Integer getTypeByName(String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        }
        for (OperationTypeEnum object : OperationTypeEnum.values()) {
            if (StringUtils.equals(name, object.getName())) {
                return object.getType();
            }
        }
        return null;
    }

    /**
     * 根据type获取name
     *
     * @param type
     * @return
     */
    public static String getNameByType(Integer type) {
        if (null == type) {
            return null;
        }
        for (OperationTypeEnum object : OperationTypeEnum.values()) {
            if (type.equals(object.type)) {
                return object.getName();
            }
        }
        return null;
    }
}
