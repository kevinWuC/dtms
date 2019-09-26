package com.medical.dtms.logclient.task;

import com.medical.dtms.logclient.config.ObjectLoggerConfig;
import com.medical.dtms.logclient.handler.BaseExtendedTypeHandler;
import com.medical.dtms.logclient.handler.BuiltinTypeHandler;
import com.medical.dtms.logclient.http.HttpUtil;
import com.medical.dtms.logclient.model.BaseAttributeModel;
import com.medical.dtms.logclient.model.OperationModel;
import com.medical.dtms.logclient.wrapper.ClazzWrapper;
import com.medical.dtms.logclient.wrapper.FieldWrapper;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

public class LogObjectTask{
    private BaseExtendedTypeHandler baseExtendedTypeHandler;
    private String objectId;
    private String operator;
    private Integer operationName;
    private String operationAlias;
    private String extraWords;
    private String comment;
    private Object oldObject;
    private Object newObject;
    private ObjectLoggerConfig objectLoggerConfig;
    private HttpUtil httpUtil;


    public LogObjectTask(String objectId, String operator, Integer operationName, String operationAlias,
                         String extraWords, String comment,
                         Object oldObject, Object newObject, ObjectLoggerConfig objectLoggerConfig,
                         HttpUtil httpUtil, BaseExtendedTypeHandler baseExtendedTypeHandler) {
        this.objectId = objectId;
        this.operator = operator;
        this.operationName = operationName;
        this.operationAlias = operationAlias;
        this.extraWords = extraWords;
        this.comment = comment;
        this.oldObject = oldObject;
        this.newObject = newObject;
        this.objectLoggerConfig = objectLoggerConfig;
        this.httpUtil = httpUtil;
        this.baseExtendedTypeHandler = baseExtendedTypeHandler;
    }

    public void executeTask() {
        try {
            OperationModel operationModel = new OperationModel(objectLoggerConfig.getBusinessAppName(), oldObject.getClass().getSimpleName(),
                    objectId, operator, operationName, operationAlias, extraWords, comment, new Date());

            if (null != newObject && null != oldObject){
                Class modelClazz = newObject.getClass();
                Class oldModelClazz = oldObject.getClass();
                if (oldModelClazz.equals(modelClazz)) {
                    ClazzWrapper clazzWrapper = new ClazzWrapper(modelClazz); // issue #1
                    List<Field> fieldList = clazzWrapper.getFieldList();
                    for (Field field : fieldList) {
                        field.setAccessible(true);
                        FieldWrapper fieldWrapper = new FieldWrapper(field, field.get(oldObject), field.get(newObject));
                        if (fieldWrapper.isWithLogTag() || "true".equals(objectLoggerConfig.getAutoLogAttributes())) {
                            if (!nullableEquals(fieldWrapper.getOldValue(), fieldWrapper.getNewValue())) {
                                BaseAttributeModel baseAttributeModel;
                                if (fieldWrapper.isWithExtendedType()) {
                                    baseAttributeModel = handleExtendedTypeItem(fieldWrapper);
                                } else {
                                    baseAttributeModel = handleBuiltinTypeItem(fieldWrapper);
                                }

                                if (baseAttributeModel != null) {
                                    operationModel.addBaseActionItemModel(baseAttributeModel);
                                }
                            }
                        }
                    }
                }
            }

            if (!operationModel.getAttributeModelList().isEmpty()) {
                httpUtil.sendLog(operationModel);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private BaseAttributeModel handleBuiltinTypeItem(FieldWrapper fieldWrapper) {
        BuiltinTypeHandler builtinType = BuiltinTypeHandler.NORMAL;
        if (fieldWrapper.getLogTag() != null) {
            builtinType = fieldWrapper.getLogTag().builtinType();
        }

        BaseAttributeModel handlerOutput = builtinType.handlerAttributeChange(fieldWrapper);

        if (handlerOutput != null) {
            // 固定值
            handlerOutput.setAttributeName(fieldWrapper.getAttributeName());
            handlerOutput.setAttributeAlias(fieldWrapper.getAttributeAlias());
            handlerOutput.setAttributeType(builtinType.name());
            return handlerOutput;
        } else {
            return null;
        }
    }

    private BaseAttributeModel handleExtendedTypeItem(FieldWrapper fieldWrapper) {
        BaseAttributeModel baseAttributeModel = baseExtendedTypeHandler.handleAttributeChange(
                fieldWrapper.getExtendedType(),
                fieldWrapper.getAttributeName(),
                fieldWrapper.getAttributeAlias(),
                fieldWrapper.getOldValue(),
                fieldWrapper.getNewValue()
        );

        if (baseAttributeModel != null) {
            if (baseAttributeModel.getAttributeType() == null) {
                baseAttributeModel.setAttributeType(fieldWrapper.getExtendedType());
            }
            if (baseAttributeModel.getAttributeName() == null) {
                baseAttributeModel.setAttributeName(fieldWrapper.getAttributeName());
            }
            if (baseAttributeModel.getAttributeAlias() == null) {
                baseAttributeModel.setAttributeAlias(fieldWrapper.getAttributeAlias());
            }
        }

        return baseAttributeModel;
    }

    // issue #2
    private boolean nullableEquals(Object a, Object b) {
        return (a == null && b == null) || (a != null && a.equals(b));
    }
}
