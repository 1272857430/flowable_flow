package cn.flow.component.exception;

public class FlowDeployException extends RuntimeException {

    public FlowDeployException(String message) {
        super(message);
    }

    public FlowDeployException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
