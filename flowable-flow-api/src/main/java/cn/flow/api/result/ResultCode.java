package cn.flow.api.result;

public enum ResultCode {
    SUCCESS(200, "success"),
    FORM_ERROR(666, "表单处理异常"),
    TASK_ERROR(666, "任务处理异常"),
    PARAM_ERROR(777, "param error"),
    NULL_DATA(888, "null data"),
    SYS_ERROR(999, "system error"),
    ERROR_DATABASE(1000, "database error");

    private int code;
    private String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
