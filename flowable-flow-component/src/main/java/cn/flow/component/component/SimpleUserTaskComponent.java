package cn.flow.component.component;

import cn.flow.component.enums.ComponentType;
import cn.flow.component.sequences.BuildSimpleSequence;
import org.flowable.bpmn.model.FlowNode;
import org.flowable.bpmn.model.SequenceFlow;

import java.util.List;

public class SimpleUserTaskComponent extends AbstractUserComponent {

    private SimpleUserTaskComponent(){
        init();
    }

    @Override
    public void init() {
        super.init();
        this.setComponentType(ComponentType.USERTASK_SIMPLE);
    }

    @Override
    public FlowNode getSelfFlowNode() {
        return userTask;
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

    public static class Builder{
        SimpleUserTaskComponent component = new SimpleUserTaskComponent();

        public Builder setAssignee(String assignee) {
            component.getUserTask().setAssignee(assignee);
            return this;
        }

        public Builder addCndidateUser(String userId){
            component.addCandidateUser(userId);
            return this;
        }

        public Builder addCndidateGroup(String groupId){
            component.addCndidateGroup(groupId);
            return this;
        }

        public Builder setPriority(String priority) {
            component.getUserTask().setPriority(priority);
            return this;
        }

        public Builder setDocumentation(String documentation) {
            component.getUserTask().setDocumentation(documentation);
            return this;
        }

        public Builder setDefaultTaskStatus(String status) {
            component.getUserTask().setCategory(status);
            return this;
        }

        public Builder setUserTaskName(String userTaskName) {
            component.setComponentName(userTaskName);
            component.getUserTask().setName(userTaskName);
            return this;
        }

        public Builder setFormClass(Class tClass) {
            component.setFormClass(tClass);
            return this;
        }

        public Builder setTaskListenerSpringBeanName(String springBeanName){
            component.setTaskListenerSpringBeanName(springBeanName);
            return this;
        }

        public Builder setDisplayIndex(Integer displayIndex) {
            component.setDisplayIndex(displayIndex);
            return this;
        }

        public Builder setDisplayable(Boolean displayable) {
            component.setDisplayable(displayable);
            return this;
        }

        public SimpleUserTaskComponent build(){
            return component;
        }
    }
}
