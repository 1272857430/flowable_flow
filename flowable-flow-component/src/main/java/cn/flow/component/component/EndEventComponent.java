package cn.flow.component.component;

import cn.flow.component.enums.ComponentType;
import cn.flow.component.utils.IdUtils;
import org.flowable.bpmn.model.EndEvent;
import org.flowable.bpmn.model.FlowNode;

/**
 * @author ponta
 * @date 2018/6/6
 * @email supreme@ponta.io
 */
public class EndEventComponent extends AbstractComponent{
    private EndEvent endEvent;

    private EndEventComponent() {
        init();
    }

    @Override
    public void init() {
        endEvent = new EndEvent();
        endEvent.setId(IdUtils.nextId());
        setComponentType(ComponentType.END_EVENT);
        setComponentName("end");
    }

    @Override
    public void buildSequenceFlow(){
        /* is over */
    }

    @Override
    public void buildExtensionElements() {
        /* is over */
    }

    @Override
    public FlowNode getSelfFlowNode() {
        return getEndEvent();
    }

    public EndEvent getEndEvent() {
        return endEvent;
    }

    public void setEndEvent(EndEvent endEvent) {
        this.endEvent = endEvent;
    }

    public static class Builder{
        EndEventComponent component = new EndEventComponent();
        public Builder setComponentName(String componentName){
            component.setComponentName(componentName);
            return this;
        }
        public EndEventComponent build(){
            return component;
        }
    }
}
