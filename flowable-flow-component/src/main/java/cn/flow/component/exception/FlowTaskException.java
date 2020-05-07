package cn.flow.component.exception;

public class FlowTaskException extends RuntimeException {

    public final static String TASK_NOT_FOUND_MESSAGE = "task not found taskId or taskName is ";

    public FlowTaskException(String message) {
        super(message);
    }
}
