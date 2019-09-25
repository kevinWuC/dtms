package com.medical.dtms.service.form;

import lombok.Data;

@Data
public class OperationForm {
    private String id;

    private String appName;

    private String objectName;

    private Long objectId;

    private String operator;

    private String operationName;

    private String operationAlias;

    public interface Query {
    }
}
