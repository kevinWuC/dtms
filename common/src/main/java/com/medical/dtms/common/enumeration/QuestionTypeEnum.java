package com.medical.dtms.common.enumeration;

import org.apache.commons.lang.StringUtils;

public enum QuestionTypeEnum {
    /**培训类别*/
    EXAMTYPEID(4, "examTypeId"),

    /**试题库类别*/
    QUESTIONSBANKTYPEID(5, "questionsBankTypeId"),

    /**试题类别*/
    QUESTIONSTYPEID(6, "questionsTypeId");

    private Integer type;

    private String name;

    QuestionTypeEnum(Integer type, String name) {
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
        for (QuestionTypeEnum object : QuestionTypeEnum.values()) {
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
        for (QuestionTypeEnum object : QuestionTypeEnum.values()) {
            if (type.equals(object.type)) {
                return object.getName();
            }
        }
        return null;
    }
}
