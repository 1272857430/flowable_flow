package cn.flow.component.template;

import cn.flow.component.component.AbstractGatewayComponent;
import cn.flow.component.component.AbstractStartComponent;
import cn.flow.component.component.IComponent;
import cn.flow.component.enums.ComponentType;
import cn.flow.component.utils.BuildExtensionElement;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.SequenceFlow;
import org.flowable.engine.delegate.ExecutionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

public class ExtensionProcess extends Process {

    private static final Logger logger = LoggerFactory.getLogger(ExtensionProcess.class);
    // 自定义流程监听
    private String executionListenerSpringBeanName;
    // 默认流程监听
    private final static String DEFAULT_EXECUTION_LISTENER_SPRING_BEAN_NAME = "defaultProcessExecutionListener";

    // 添加流程监听
    private void addExtentionElements(){
        addExtensionElement(BuildExtensionElement.buildExtensionElement(ExecutionListener.EVENTNAME_START, DEFAULT_EXECUTION_LISTENER_SPRING_BEAN_NAME));
        addExtensionElement(BuildExtensionElement.buildExtensionElement(ExecutionListener.EVENTNAME_END, DEFAULT_EXECUTION_LISTENER_SPRING_BEAN_NAME));

        if(!StringUtils.isEmpty(executionListenerSpringBeanName)){
            addExtensionElement(BuildExtensionElement.buildExtensionElement(ExecutionListener.EVENTNAME_START, getExecutionListenerSpringBeanName()));
            addExtensionElement(BuildExtensionElement.buildExtensionElement(ExecutionListener.EVENTNAME_END, getExecutionListenerSpringBeanName()));
            addExtensionElement(BuildExtensionElement.buildExtensionElement(ExecutionListener.EVENTNAME_TAKE, getExecutionListenerSpringBeanName()));
        }
    }

    public String getExecutionListenerSpringBeanName() {
        return executionListenerSpringBeanName;
    }

    public void setExecutionListenerSpringBeanName(String executionListenerSpringBeanName) {
        this.executionListenerSpringBeanName = executionListenerSpringBeanName;
    }

    public static class Builder{
        ExtensionProcess process = new ExtensionProcess();

        public Builder setProcessName(String processName){
            process.setName(processName);
            return this;
        }

        public Builder setProcessKey(String processKey){
            process.setId(processKey);
            return this;
        }

        public Builder setExectutionListenerSpringBeanName(String name){
            process.setExecutionListenerSpringBeanName(name);
            return this;
        }

        public Builder setDocumentation(String documentation){
            process.setDocumentation(documentation);
            return this;
        }

        public ExtensionProcess build(){
            process.addExtentionElements();
            return process;
        }
    }

    public BpmnModel buildBpmnModel(AbstractStartComponent startEventComponent, ExtensionProcess process){
        BpmnModel bm = new BpmnModel();
        buildComponent(startEventComponent);
        buildFlowElement(startEventComponent,process);
        bm.addProcess(process);
        return bm;
    }

    private boolean flowElementExist(Process process,String flowElementId){
        FlowElement flowElement = process.getFlowElement(flowElementId);
        if(flowElement != null){
            return true;
        }
        return false;
    }

    private void buildFlowElement(IComponent component, Process process){
        ComponentType componentType = component.getComponentType();
        if(componentType == ComponentType.STARTEVENT_EXCLUSIVE ||
                componentType == ComponentType.USERTASK_EXCLUSIVE ||
                componentType == ComponentType.GATEWAY_PARALLEL ||
                componentType == ComponentType.GATEWAY_INCLUSIVE){
            AbstractGatewayComponent gatewayComponent = (AbstractGatewayComponent) component;
            if(!flowElementExist(process,component.getSelfFlowNode().getId())){
                process.addFlowElement(component.getSelfFlowNode());
            }

            if(!flowElementExist(process,gatewayComponent.getGateway().getId())){
                process.addFlowElement(gatewayComponent.getGateway());
            }

            List<SequenceFlow> sequenceFlows = component.getSequenceFlows();
            for (SequenceFlow seq:sequenceFlows){
                if(!flowElementExist(process,seq.getId())){
                    process.addFlowElement(seq);
                }
            }
        }

        if(componentType == ComponentType.STARTEVENT_SIMPLE ||
                componentType == ComponentType.USERTASK_SIMPLE ||
                componentType == ComponentType.SERVICE_TASK ||
                componentType == ComponentType.END_EVENT ||
                componentType == ComponentType.RECEIVE_TASK){
            if(!flowElementExist(process,component.getSelfFlowNode().getId())){
                process.addFlowElement(component.getSelfFlowNode());
            }

            List<SequenceFlow> sequenceFlows = component.getSequenceFlows();
            for (SequenceFlow seq:sequenceFlows){
                if(!flowElementExist(process,seq.getId())){
                    process.addFlowElement(seq);
                }
            }
        }

        component.setIsAddedElement(true);
        Map<Integer, IComponent> nextComponents = component.getNextComponents();
        if(nextComponents != null && nextComponents.size() >0){
            for (Integer key:nextComponents.keySet()){
                IComponent nextComponent = nextComponents.get(key);
                if(nextComponent.isAddedElement()){
                    continue;
                }
                buildFlowElement(nextComponent,process);
            }
        }
    }

    /**
     * 构建组件
     * @param component
     */
    private void buildComponent(IComponent component){
        component.buildExtensionElements();
        component.buildSequenceFlow();
        component.setIsBuildComponent(true);

        // 任务定义的展示和顺序添加缓存
//        if(component instanceof AbstractUserComponent){
//            AbstractUserComponent abstractUserTaskComponent = (AbstractUserComponent) component;
//            abstractUserTaskComponent.addComponentCache();
//        }

        Map<Integer, IComponent> components = component.getNextComponents();
        for (Integer key : components.keySet()) {
            IComponent nextComponent = components.get(key);
            if(nextComponent != null){
                if(nextComponent.isBuildComponent()){
                    continue;
                }
                buildComponent(nextComponent);
            }else{
                logger.error("Component objects cannot be null");
            }
        }
    }
}
