package com.medical.dtms.common.model.user;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;
import lombok.Data;

/**
 * @version： SimpleUserModel.java v 1.0, 2019年08月27日 17:11 wuxuelin Exp$
 * @Description 简易 用户model
 **/
@Data
public class SimpleUserModel {
    /**
     * 用户id
     */
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long userId;
    /**
     * 用户名
     */
    private String userName;
}
