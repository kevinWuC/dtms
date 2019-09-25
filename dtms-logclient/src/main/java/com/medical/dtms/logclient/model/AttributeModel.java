package com.medical.dtms.logclient.model;

public class AttributeModel extends BaseAttributeModel {
    private Long id;
    private Long operationId;

    public AttributeModel() {
    }

    public AttributeModel(BaseAttributeModel baseAttributeModel) {
        this.setAttributeType(baseAttributeModel.getAttributeType());
        this.setAttributeName(baseAttributeModel.getAttributeName());
        this.setAttributeAlias(baseAttributeModel.getAttributeAlias());
        this.setOldValue(baseAttributeModel.getOldValue());
        this.setNewValue(baseAttributeModel.getNewValue());
        this.setDiffValue(baseAttributeModel.getDiffValue());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOperationId() {
        return operationId;
    }

    public void setOperationId(Long operationId) {
        this.operationId = operationId;
    }
}
