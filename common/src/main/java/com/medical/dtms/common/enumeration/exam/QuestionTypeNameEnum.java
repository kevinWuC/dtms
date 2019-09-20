package com.medical.dtms.common.enumeration.exam;


import org.apache.commons.lang.StringUtils;

/**
 * @version： QuestionTypeNameEnum.java v 1.0, 2019年09月19日 14:54 huangshuaiquan Exp$
 * @Description
 **/
public enum QuestionTypeNameEnum {
    /**单选题*/
    TYPE_SINGLE_CHOICE_QUESTION(1,"单选题"),
    /**多选题*/
    TYPE_MULTIPLE_CHOICES_QUESTION(2,"多选题"),
    /**判断题*/
    TYPE_JUDGE_QUESTION(3,"判断题"),
    /**问答题*/
    TYPE_ESSAY_QUESTION(4,"问答题"),
    /**简述题*/
    TYPE_SKETCH_QUESTION(5,"简述题");


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

    QuestionTypeNameEnum(Integer type, String name) {
        this.type = type;
        this.name = name;
    }


    /**
     * 根据name获取type
     *
     * @param name
     * @return
     */
    public static Integer getTypeByName(String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        }
        for (QuestionTypeNameEnum object : QuestionTypeNameEnum.values()) {
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
        for (QuestionTypeNameEnum object : QuestionTypeNameEnum.values()) {
            if (type.equals(object.type)) {
                return object.getName();
            }
        }
        return null;
    }


}
