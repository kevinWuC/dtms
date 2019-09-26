package com.medical.dtms.service.form;

import lombok.Data;

@Data
public class OperationForm {
    private Long id;

    private String appName;

    private String objectName;

    private String objectId;

    private String operator;

    private Integer operationName;

    private String operationAlias;

    public interface Query {
    }
}
