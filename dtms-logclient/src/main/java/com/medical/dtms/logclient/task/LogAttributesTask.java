package com.medical.dtms.logclient.task;

import com.google.gson.Gson;
import com.medical.dtms.logclient.config.ObjectLoggerConfig;
import com.medical.dtms.logclient.http.HttpUtil;
import com.medical.dtms.logclient.model.BaseAttributeModel;
import com.medical.dtms.logclient.model.OperationModel;

import java.util.Date;
import java.util.List;

public class LogAttributesTask implements Runnable {
    private HttpUtil httpUtil;
    private String objectName;
    private String objectId;
    private String operator;
    private String operationName;
    private String operationAlias;
    private String extraWords;
    private String comment;
    private ObjectLoggerConfig objectLoggerConfig;

    private List<BaseAttributeModel> baseAttributeModelList;

    public LogAttributesTask(String objectName, String objectId, String operator, String operationName, String operationAlias,
                             String extraWords, String comment,
                             List<BaseAttributeModel> baseAttributeModelList, ObjectLoggerConfig objectLoggerConfig, HttpUtil httpUtil) {
        this.objectName = objectName;
        this.objectId = objectId;
        this.operator = operator;
        this.operationName = operationName;
        this.operationAlias = operationAlias;
        this.extraWords = extraWords;
        this.comment = comment;
        this.baseAttributeModelList = baseAttributeModelList;
        this.objectLoggerConfig = objectLoggerConfig;
        this.httpUtil = httpUtil;
    }

    @Override
    public void run() {
        try {
            OperationModel operationModel = new OperationModel(objectLoggerConfig.getBusinessAppName(), objectName, objectId, operator,
                    operationName, operationAlias, extraWords, comment, new Date());
            if (baseAttributeModelList != null && !baseAttributeModelList.isEmpty()) {
                operationModel.addBaseActionItemModelList(baseAttributeModelList);
            }
            httpUtil.sendLog(new Gson().toJson(operationModel));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
