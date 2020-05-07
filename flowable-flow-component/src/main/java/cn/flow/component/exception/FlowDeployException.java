package cn.flow.component.exception;

public class FlowDeployException extends RuntimeException {

    public final static String FORM_NOT_FOUND_EXCEPTION = "form not found formKey or deploymentId is ";

    public FlowDeployException(String message) {
        super(message);
    }

    public FlowDeployException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
