package cn.flow.server.listener;
import org.flowable.common.engine.api.delegate.event.FlowableEvent;
import org.flowable.common.engine.api.delegate.event.FlowableEventListener;

public  class MyEventListener implements FlowableEventListener {

    @Override
    public void onEvent(FlowableEvent event) {
        System.out.println(event.getType());
    }

    @Override
    public boolean isFailOnException() {
        return false;
    }

    @Override
    public boolean isFireOnTransactionLifecycleEvent() {
        return false;
    }

    @Override
    public String getOnTransaction() {
        return null;
    }
}
