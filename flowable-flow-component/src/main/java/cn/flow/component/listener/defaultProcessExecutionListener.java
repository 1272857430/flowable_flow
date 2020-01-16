package cn.flow.component.listener;

import org.flowable.engine.delegate.DelegateExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("defaultProcessExecutionListener")
public class defaultProcessExecutionListener extends CustomExecutionListener {

    private final static Logger logger = LoggerFactory.getLogger(defaultProcessExecutionListener.class);

    @Override
    public void start(DelegateExecution execution) {
        logger.info("processInstance: " + execution.getProcessInstanceId() + "---Start");
    }

    @Override
    public void end(DelegateExecution execution) {
        logger.info("processInstance: " + execution.getProcessInstanceId() + "---End");
    }

    @Override
    public void take(DelegateExecution execution) {
    }
}
