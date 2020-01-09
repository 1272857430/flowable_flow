package cn.flow.component.sequences;

import cn.flow.component.component.IComponent;
import org.flowable.bpmn.model.SequenceFlow;

import java.util.ArrayList;
import java.util.List;

public class BuildSimpleSequence extends AbstractBuildSequence {

    @Override
    public List<SequenceFlow> linkSingleSequenceFlow(IComponent component, Boolean addListener) {
        List<SequenceFlow> sequenceFlows = new ArrayList<>();
        SequenceFlow sequenceFlow = linkSingleSequenceFlow(component.getSelfFlowNode(), component.getNextComponent(0).getSelfFlowNode());
        sequenceFlows.add(sequenceFlow);
        return sequenceFlows;
    }
}
