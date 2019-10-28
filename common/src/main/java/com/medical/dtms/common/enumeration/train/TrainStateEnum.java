package com.medical.dtms.common.enumeration.train;

import org.apache.commons.lang.StringUtils;

/**
 * @version： TrainStateEnum.java v 1.0, 2019年10月12日 14:18 wuxuelin Exp$
 * @Description 培训阶段 枚举类
 **/
public enum TrainStateEnum {

    TRAIN_STATE_ENUM_1(-1, "未开始"),
    TRAIN_STATE_ENUM_2(0, "正进行"),
    TRAIN_STATE_ENUM_3(1, "已完成");

    private Integer state;

    private String name;

    TrainStateEnum(Integer state, String name) {
        this.state = state;
        this.name = name;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 根据 name 获取 state
     *
     * @param code
     * @return
     */
    public static Integer getStateByName(String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        }
        for (TrainStateEnum object : TrainStateEnum.values()) {
            if (StringUtils.equals(name, object.getName())) {
                return object.getState();
            }
        }
        return null;
    }

    /**
     * 根据 state 获取 name
     *
     * @param type
     * @return
     */
    public static String getNameByState(Integer state) {
        if (null == state) {
            return null;
        }
        for (TrainStateEnum object : TrainStateEnum.values()) {
            if (state.equals(object.getState())) {
                return object.getName();
            }
        }
        return null;
    }
}
