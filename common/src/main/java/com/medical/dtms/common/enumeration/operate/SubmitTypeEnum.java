package com.medical.dtms.common.enumeration.operate;

import org.apache.commons.lang.StringUtils;

/**
 * @version： SubmitTypeEnum.java v 1.0, 2019年08月19日 16:05 wuxuelin Exp$
 * @Description 提交类型枚举类
 **/
public enum SubmitTypeEnum {

    SubmitTypeEnum_1(1, "保存"),
    SubmitTypeEnum_2(2, "通过审批"),
    SubmitTypeEnum_3(3, "退回"),
    SubmitTypeEnum_4(4, "提交"),
    SubmitTypeEnum_5(5, "直送"),
    SubmitTypeEnum_6(6, "委托"),
    SubmitTypeEnum_7(7, "申请修改"),
    SubmitTypeEnum_8(8, "申请作废"),
    SubmitTypeEnum_9(9, "修改"),
    SubmitTypeEnum_10(10, "审核通过"),
    SubmitTypeEnum_11(11, "拒绝通过");

    private Integer type;

    private String name;

    SubmitTypeEnum(Integer type, String name) {
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
     * 根据name获取type
     *
     * @param code
     * @return
     */
    public static Integer getTypeByName(String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        }
        for (SubmitTypeEnum object : SubmitTypeEnum.values()) {
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
        for (SubmitTypeEnum object : SubmitTypeEnum.values()) {
            if (type.equals(object.type)) {
                return object.getName();
            }
        }
        return null;
    }
}
