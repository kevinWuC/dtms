package com.medical.dtms.logclient.handler;

import com.medical.dtms.logclient.model.BaseAttributeModel;
import com.medical.dtms.logclient.richText.Html2Text;
import com.medical.dtms.logclient.richText.RichTextHandler;
import com.medical.dtms.logclient.wrapper.FieldWrapper;

public enum BuiltinTypeHandler {
    NORMAL {
        @Override
        public BaseAttributeModel handlerAttributeChange(FieldWrapper fieldWrapper) {
            BaseAttributeModel baseAttributeModel = new BaseAttributeModel();
            baseAttributeModel.setOldValue(fieldWrapper.getOldValueString());
            baseAttributeModel.setNewValue(fieldWrapper.getNewValueString());
            return baseAttributeModel;
        }
    },
    RICHTEXT {
        @Override
        public BaseAttributeModel handlerAttributeChange(FieldWrapper fieldWrapper) {
            String simpleOldValue = Html2Text.simpleHtml(fieldWrapper.getOldValueString());
            String simpleNewValue = Html2Text.simpleHtml(fieldWrapper.getNewValueString());
            // Delete the format and leave the main content behind.
            if (simpleOldValue == null || simpleNewValue == null || simpleOldValue.equals(simpleNewValue)) {
                // The main content is the same, the same
                return null;
            } else {
                BaseAttributeModel baseAttributeModel = new BaseAttributeModel();
                baseAttributeModel.setOldValue(fieldWrapper.getOldValueString());
                baseAttributeModel.setNewValue(fieldWrapper.getNewValueString());
                baseAttributeModel.setDiffValue(RichTextHandler.diffText(fieldWrapper.getOldValueString(), fieldWrapper.getNewValueString()));
                return baseAttributeModel;
            }
        }
    };

    public abstract BaseAttributeModel handlerAttributeChange(FieldWrapper fieldWrapper);
}
