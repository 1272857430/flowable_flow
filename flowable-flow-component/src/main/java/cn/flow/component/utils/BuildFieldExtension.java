package cn.flow.component.utils;

import org.flowable.bpmn.model.FieldExtension;

public class BuildFieldExtension {

    public static FieldExtension createFieldExtension(String fieldName, String fieldValue) {

        FieldExtension fieldExtension = new FieldExtension();
        fieldExtension.setFieldName(fieldName);
        fieldExtension.setStringValue(fieldValue);
//        fieldExtension.setExpression(fieldName);

        return fieldExtension;
    }
}
