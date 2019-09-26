package com.medical.dtms.service.dataobject.datasource;

import com.medical.dtms.logclient.annotation.LogTag;
import com.medical.dtms.logclient.handler.BuiltinTypeHandler;
import lombok.Data;

/**
 * @version： BackUpModel.java v 1.0, 2019年09月26日 16:35 wuxuelin Exp$
 * @Description 备份 model
 **/
@Data
public class BackUpModel {

    /**
     * 备注
     */
    @LogTag(alias = "account", builtinType = BuiltinTypeHandler.NORMAL)
    private String comment;
}
