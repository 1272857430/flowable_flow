package cn.flow.api.result;

public enum ResultCode {
    SUCCESS(200, "success"),
    PARAM_ERROR(777, "param error"),
    NULL_DATA(888, "null data"),
    SYS_ERROR(999, "system error");

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
