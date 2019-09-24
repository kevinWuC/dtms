package com.medical.dtms.logclient.handler;


import com.medical.dtms.logclient.model.BaseAttributeModel;
import org.springframework.stereotype.Component;

@Component
public interface BaseExtendedTypeHandler {
    BaseAttributeModel handleAttributeChange(String extendedType, String attributeName, String attributeAlias, Object oldValue, Object newValue);
}
