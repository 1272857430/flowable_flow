package cn.flow.component.component;

import cn.flow.component.enums.ComponentType;
import org.flowable.bpmn.model.FlowNode;
import org.flowable.bpmn.model.SequenceFlow;

import java.util.List;
import java.util.Map;

public interface IComponent {

    /**
     * 组件初始化
     */
    void init();

    FlowNode getSelfFlowNode();

    /**
     * 获取连线
     **/
    List<SequenceFlow> getSequenceFlows();

    /**
     * 存取下标的组件
     **/
    void setNextComponent(int index, IComponent component);

    Map<Integer, IComponent> getNextComponents();

    IComponent getNextComponent(int index);

    /**
     * 组件名 组件类型
     **/
    void setComponentName(String componentName);

    String getComponentName();

    void setComponentType(ComponentType componentType);

    ComponentType getComponentType();

    /**
     * 是否构建过
     **/
    boolean isBuildComponent();

    void setIsBuildComponent(boolean isBuildComponent);

    /**
     * 是否添加过
     **/
    boolean isAddedElement();

    void setIsAddedElement(boolean isAddedElement);

    /**
     * 是否发布过表单
     **/
    Boolean isBuildForm();

    void setIsBuildForm(Boolean isBuildForm);

    /**
     * 是否展示
     **/
    Boolean isSaveDisplay();

    void setIsSaveDisplay(Boolean isSaveDisplay);

    /**
     * 构建
     **/
    void buildSequenceFlow();

    /**
     * 配置监听
     */
    void buildExtensionElements();

}
