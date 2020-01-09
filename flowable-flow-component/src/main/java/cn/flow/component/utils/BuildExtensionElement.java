package cn.flow.component.utils;

import com.alibaba.fastjson.JSONObject;
import org.flowable.bpmn.model.ExtensionAttribute;
import org.flowable.bpmn.model.ExtensionElement;
import org.flowable.bpmn.model.ImplementationType;
import org.springframework.util.StringUtils;

public class BuildExtensionElement {

    private static final String executionListener = "flowable:executionListener";
    private static final String taskListener = "flowable:taskListener";

    /**
     * 添加 executionListener 监听 delegateExpression形式
     */
    public static ExtensionElement buildExtensionElement(String eventName, String springBeanName){
        return buildExtensionElement(executionListener, eventName, ImplementationType.IMPLEMENTATION_TYPE_DELEGATEEXPRESSION, "${"+springBeanName+"}");
    }

    /**
     * 添加 executionListener 监听 class形式
     */
    public static ExtensionElement buildClassExtension(String eventName,String className){
        return buildExtensionElement(executionListener, eventName, ImplementationType.IMPLEMENTATION_TYPE_CLASS, className);
    }

    /**
     * 添加 taskListener 监听，delegateExpression 形式
     */
    public static ExtensionElement buildTaskListenerElement(String eventName,String springBeanName){
        return buildExtensionElement(taskListener, eventName, ImplementationType.IMPLEMENTATION_TYPE_DELEGATEEXPRESSION, "${"+springBeanName+"}");
    }

    /**
     * 添加 taskListener 监听，class 形式
     */
    public static ExtensionElement buildClassTaskListener(String eventName,String className){
        return buildExtensionElement(taskListener, eventName, ImplementationType.IMPLEMENTATION_TYPE_CLASS, className);
    }

    private static ExtensionElement buildExtensionElement(String extensionName, String eventName, String attr2Name, String attr2Value) {
        ExtensionElement element = new ExtensionElement();
        element.setName(extensionName);

        ExtensionAttribute extensionAttribute1 = new ExtensionAttribute();
        extensionAttribute1.setName("event");
        extensionAttribute1.setValue(eventName);

        ExtensionAttribute extensionAttribute2 = new ExtensionAttribute();
        extensionAttribute2.setName(attr2Name);
        extensionAttribute2.setValue(attr2Value);

        element.addAttribute(extensionAttribute1);
        element.addAttribute(extensionAttribute2);

        return element;
    }

    /**
     * 给 task 添加 flowable:field
     */
    public static ExtensionElement builderTaskFieldElement(String attributeName, String attributeValue) {
        ExtensionElement extensionElement = new ExtensionElement();
        extensionElement.setName("flowable:field");

        ExtensionAttribute parentAttribute = new ExtensionAttribute();
        parentAttribute.setName("name");
        parentAttribute.setValue(attributeName);
        extensionElement.setElementText(attributeValue);

        extensionElement.addAttribute(parentAttribute);

        return extensionElement;
    }

    /**
     * flowable:formProperty
     */
    public static ExtensionElement builderFormElement(JSONObject jsonObject) {
        ExtensionElement extensionElement = new ExtensionElement();
        extensionElement.setName("flowable:formProperty");
        for (String key : jsonObject.keySet()) {
            if (StringUtils.isEmpty(jsonObject.get(key)))
                continue;
            ExtensionAttribute extensionAttribute = new ExtensionAttribute();
            extensionAttribute.setName(key);
            extensionAttribute.setValue(String.valueOf(jsonObject.get(key)));
            extensionElement.addAttribute(extensionAttribute);
        }
        return extensionElement;
    }
}
