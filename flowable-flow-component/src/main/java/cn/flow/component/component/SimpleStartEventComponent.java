package cn.flow.component.component;

import cn.flow.component.enums.ComponentType;
import cn.flow.component.sequences.BuildSimpleSequence;
import org.flowable.bpmn.model.FlowNode;
import org.flowable.bpmn.model.SequenceFlow;

import java.util.List;

public class SimpleStartEventComponent extends AbstractStartComponent {

    public SimpleStartEventComponent() {
        init();
    }

    @Override
    public void init() {
        super.init();
        setComponentType(ComponentType.STARTEVENT_SIMPLE);
    }

    @Override
    public FlowNode getSelfFlowNode() {
        return getStartEvent();
    }

    @Override
    public void buildSequenceFlow() {
        getSequenceFlows().clear();
        List<SequenceFlow> sequenceFlows = new BuildSimpleSequence().linkSingleSequenceFlow(this, null);
        getSequenceFlows().addAll(sequenceFlows);
    }

    public void setNextComponent(IComponent component){
        setNextComponent(0,component);
    }

    public IComponent getNextComponent() {
        return super.getNextComponent(0);
    }

    public static class Builder {
        SimpleStartEventComponent component = new SimpleStartEventComponent();

        public Builder setComponentName(String componentName){
            component.setComponentName(componentName);
            return this;
        }

        public Builder setStartEventName(String startEventName) {
            component.getStartEvent().setName(startEventName);
            return this;
        }

        public Builder setFormClass(Class tClass) {
            component.setFormClass(tClass);
            return this;
        }

        public SimpleStartEventComponent build(){
            return component;
        }
    }
}
