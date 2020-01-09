package cn.flow.component.component;

import cn.flow.component.form.TranslateFieldAnnotation;
import cn.flow.component.utils.IdUtils;
import org.flowable.bpmn.model.StartEvent;

import java.util.Map;

public abstract class AbstractStartComponent extends AbstractComponent {

    private StartEvent startEvent;
    private Class formClass;

    @Override
    public void init() {
        startEvent = new StartEvent();
        startEvent.setId(IdUtils.nextId());
        startEvent.setName("启动节点");
        setComponentName("start");
    }

    public StartEvent getStartEvent() {
        return startEvent;
    }

    public Class getFormClass() {
        return formClass;
    }

    public void setFormClass(Class tClass){
        this.formClass = tClass;
        this.getStartEvent().setFormKey(TranslateFieldAnnotation.getFormKey(tClass));
    }

    @Override
    public void buildExtensionElements() {

    }
}
