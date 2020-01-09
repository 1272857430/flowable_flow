package cn.flow.component.sequences;

import cn.flow.component.utils.IdUtils;
import org.apache.commons.lang.StringUtils;
import org.flowable.bpmn.model.FlowNode;
import org.flowable.bpmn.model.SequenceFlow;

import java.util.List;

public abstract class AbstractBuildSequence implements BuildSequence {

    String GATEWAY_NEXT_TASK ="gatewayNextTask";

    /**
     * 普通连线
     */
    SequenceFlow linkSingleSequenceFlow(FlowNode fromTask, FlowNode toTask){
        return linkSingleSequenceFlow(fromTask,toTask,null);
    }

    /**
     * 带有表达式的连线
     */
    SequenceFlow linkSingleSequenceFlow(FlowNode fromTask, FlowNode toTask, String conditionExpressio){
        SequenceFlow flow = new SequenceFlow();
        flow.setId(IdUtils.nextId());
        if (!StringUtils.isBlank(conditionExpressio)) {
            flow.setConditionExpression(conditionExpressio);
        }
        List<SequenceFlow> outgoingFlows = fromTask.getOutgoingFlows();
        outgoingFlows.add(flow);
        fromTask.setOutgoingFlows(outgoingFlows);
        List<SequenceFlow> incomingFlows = toTask.getIncomingFlows();
        incomingFlows.add(flow);
        toTask.setIncomingFlows(incomingFlows);
        flow.setSourceRef(fromTask.getId());
        flow.setTargetRef(toTask.getId());
        return flow;
    }

    String buildConditionExpression(String key, int value){
        return "${"+key+"=="+value+"}";
    }
}
