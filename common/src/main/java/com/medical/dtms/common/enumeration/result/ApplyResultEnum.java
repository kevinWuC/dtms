package com.medical.dtms.common.enumeration.result;

import org.apache.commons.lang.StringUtils;

/**
 * @version： ApplyResultEnum.java v 1.0, 2019年08月27日 15:27 wuxuelin Exp$
 * @Description 申请结果枚举类
 **/
public enum ApplyResultEnum {

    ApplyResultEnum_1(1, "提交"),
    ApplyResultEnum_2(2, "待处理"),
    ApplyResultEnum_3(3, "拒绝"),
    ApplyResultEnum_4(4, "ok");

    private Integer result;

    private String name;

    ApplyResultEnum(Integer result, String name) {
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
        for (ApplyResultEnum object : ApplyResultEnum.values()) {
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
        for (ApplyResultEnum object : ApplyResultEnum.values()) {
            if (result.equals(object.result)) {
                return object.getName();
            }
        }
        return null;
    }
}
