package com.medical.dtms.common.enumeration.result;

import org.apache.commons.lang.StringUtils;

/**
 * @version： LogResultEnum.java v 1.0, 2019年08月31日 16:39 wuxuelin Exp$
 * @Description 日志结果枚举类
 **/
public enum LogResultEnum {

    LOG_RESULT_ENUM_1(1, "同意"),
    LOG_RESULT_ENUM_2(2, "退回"),
    LOG_RESULT_ENUM_3(3, "待处理"),
    LOG_RESULT_ENUM_4(4, "ok"),
    LOG_RESULT_ENUM_5(5, "委托"),
    ;

    private Integer result;

    private String name;

    LogResultEnum(Integer result, String name) {
        this.result = result;
        this.name = name;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 根据 name 获取 result
     *
     * @param code
     * @return
     */
    public static Integer getResultByName(String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        }
        for (LogResultEnum object : LogResultEnum.values()) {
            if (StringUtils.equals(name, object.getName())) {
                return object.getResult();
            }
        }
        return null;
    }

    /**
     * 根据 result 获取 name
     *
     * @param type
     * @return
     */
    public static String getNameByResult(Integer result) {
        if (null == result) {
            return null;
        }
        for (LogResultEnum object : LogResultEnum.values()) {
            if (result.equals(object.result)) {
                return object.getName();
            }
        }
        return null;
    }
}
