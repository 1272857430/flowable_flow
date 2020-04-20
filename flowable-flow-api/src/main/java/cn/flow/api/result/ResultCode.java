package cn.flow.api.result;

public enum ResultCode {
    SUCCESS(200, "success"),
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
