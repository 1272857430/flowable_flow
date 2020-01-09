package cn.flow.api.enums;

/**
 * @author ponta
 * @date 2018/8/15
 * @email supreme@ponta.io
 */
public enum ProcessStatus {
    /**
     * 已结束
     */
    FINISHED(1),
    /**
     * 进行中
     */
    UNFINISHED(2);

    private int code;

    ProcessStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
