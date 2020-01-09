package cn.flow.component.sequences;

import cn.flow.component.component.IComponent;
import org.flowable.bpmn.model.SequenceFlow;

import java.util.List;

public interface BuildSequence {

    List<SequenceFlow> linkSingleSequenceFlow(IComponent component, Boolean addListener);
}
