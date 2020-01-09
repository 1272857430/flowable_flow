package cn.flow.component.sequences;

import cn.flow.component.component.AbstractGatewayComponent;
import cn.flow.component.component.IComponent;
import cn.flow.component.utils.BuildExtensionElement;
import org.flowable.bpmn.model.SequenceFlow;
import org.flowable.engine.delegate.BaseExecutionListener;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BuildGatewaySequence extends AbstractBuildSequence {

    @Override
    public List<SequenceFlow> linkSingleSequenceFlow(IComponent component, Boolean addListener) {
        AbstractGatewayComponent abstractGateWayComponent = (AbstractGatewayComponent) component;
        List<SequenceFlow> sequenceFlows = new ArrayList<>();
        Map<Integer, IComponent> nextComponents = abstractGateWayComponent.getNextComponents();
        if (CollectionUtils.isEmpty(nextComponents)) {
            throw new RuntimeException("gateway must has at least 2 speak");
        }
        if (addListener == null || !addListener) {
            return sequenceFlows;
        }
        // 给网关后面的直接连线添加监听
        nextComponents.forEach((key, value) -> {
            SequenceFlow sequenceFlow = linkSingleSequenceFlow(abstractGateWayComponent.getGateway(), value.getSelfFlowNode(), buildConditionExpression(GATEWAY_NEXT_TASK, key));
            if (!StringUtils.isEmpty(abstractGateWayComponent.getGatewayAdapterSpringBeanName())) {
                sequenceFlow.addExtensionElement(BuildExtensionElement.buildExtensionElement(BaseExecutionListener.EVENTNAME_START, abstractGateWayComponent.getGatewayAdapterSpringBeanName()));
                sequenceFlow.addExtensionElement(BuildExtensionElement.buildExtensionElement(BaseExecutionListener.EVENTNAME_END, abstractGateWayComponent.getGatewayAdapterSpringBeanName()));
                sequenceFlow.addExtensionElement(BuildExtensionElement.buildExtensionElement(BaseExecutionListener.EVENTNAME_TAKE, abstractGateWayComponent.getGatewayAdapterSpringBeanName()));
            }
            sequenceFlows.add(sequenceFlow);
        });
        return sequenceFlows;
    }
}
