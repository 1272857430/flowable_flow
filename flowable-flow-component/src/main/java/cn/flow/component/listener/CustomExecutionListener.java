package cn.flow.component.listener;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.ExecutionListener;

public abstract class CustomExecutionListener implements ExecutionListener {
    public abstract void start(DelegateExecution execution);
    public abstract void end(DelegateExecution execution) throws Exception;
    public abstract void take(DelegateExecution execution);

    @Override
    public void notify(DelegateExecution execution) {
        String eventName = execution.getEventName();
        switch (eventName){
            case EVENTNAME_START:{
                start(execution);
                break;
            }
            case EVENTNAME_END:{
                try {
                    end(execution);
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            }
            case EVENTNAME_TAKE:{
                take(execution);
                break;
            }
            default:{
                break;
            }
        }
    }
}
