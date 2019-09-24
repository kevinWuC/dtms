package com.medical.dtms.logclient.service;

import com.google.gson.Gson;
import com.medical.dtms.logclient.model.OperationModel;
import org.springframework.stereotype.Component;

@Component
public class LogServer {

    public OperationModel resolveOperationModel(String operationModelString) {
        return new Gson().fromJson(operationModelString,OperationModel.class);
    }
}
