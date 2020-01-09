package cn.flow.component.component;

import cn.flow.component.enums.ComponentType;
import org.flowable.bpmn.model.SequenceFlow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractComponent implements IComponent {

    private String componentName;
    private ComponentType componentType;
    private boolean buildComponent;
    private boolean addedElement;
    private boolean buildForm;
    private boolean isSaveDisplay;
    private String taskListenerSpringBeanName;
    private Map<Integer,IComponent> nextComponentMap = new HashMap<>();
    private List<SequenceFlow> sequenceFlows =new ArrayList<>();

    @Override
    public List<SequenceFlow> getSequenceFlows() {
        return sequenceFlows;
    }

    @Override
    public void setNextComponent(int index, IComponent component) {
        if(component != null){
            nextComponentMap.put(index,component);
        }
    }

    @Override
    public Map<Integer, IComponent> getNextComponents() {
        return nextComponentMap;
    }

    @Override
    public IComponent getNextComponent(int index) {
        if(nextComponentMap.size() > 0){
            return nextComponentMap.get(index);
        }else {
            return null;
        }
    }

    @Override
    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    @Override
    public String getComponentName() {
        return componentName;
    }

    @Override
    public void setComponentType(ComponentType componentType) {
        this.componentType = componentType;
    }

    @Override
    public ComponentType getComponentType() {
        return componentType;
    }

    @Override
    public boolean isBuildComponent() {
        return buildComponent;
    }

    @Override
    public void setIsBuildComponent(boolean isBuildComponent) {
        this.buildComponent = isBuildComponent;
    }

    @Override
    public boolean isAddedElement() {
        return addedElement;
    }

    @Override
    public void setIsAddedElement(boolean isAddedElement) {
        this.addedElement = isAddedElement;
    }

    @Override
    public Boolean isBuildForm() {
        return buildForm;
    }

    @Override
    public void setIsBuildForm(Boolean isBuildForm) {
        this.buildForm = isBuildForm;
    }

    @Override
    public Boolean isSaveDisplay() {
        return isSaveDisplay;
    }

    @Override
    public void setIsSaveDisplay(Boolean isSaveDisplay) {
        this.isSaveDisplay = isSaveDisplay;
    }

    public String getTaskListenerSpringBeanName() {
        return taskListenerSpringBeanName;
    }

    public void setTaskListenerSpringBeanName(String taskListenerSpringBeanName) {
        this.taskListenerSpringBeanName = taskListenerSpringBeanName;
    }
}
