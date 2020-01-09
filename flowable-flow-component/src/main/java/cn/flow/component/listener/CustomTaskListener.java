package cn.flow.component.listener;

import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;

public abstract class CustomTaskListener implements TaskListener{
    public abstract void create(DelegateTask delegateTask);
    public abstract void complete(DelegateTask delegateTask);
    public abstract void delete(DelegateTask delegateTask);
    public abstract void assignment(DelegateTask delegateTask);

    @Override
    public void notify(DelegateTask delegateTask) {
        String eventName = delegateTask.getEventName();
        switch (eventName){
            case EVENTNAME_CREATE:{
                create(delegateTask);
                break;
            }
            case EVENTNAME_COMPLETE:{
                complete(delegateTask);
                break;
            }
            case EVENTNAME_DELETE:{
                delete(delegateTask);
                break;
            }
            case EVENTNAME_ASSIGNMENT:{
                assignment(delegateTask);
                break;
            }
            default:{
                break;
            }
        }
    }
}
