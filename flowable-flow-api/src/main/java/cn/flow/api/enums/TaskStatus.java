package cn.flow.api.enums;

/**
 * @author ponta
 * @date 2018/8/9
 * @email supreme@ponta.io
 */
public enum TaskStatus {

    ALL(0),
    /**
     * 等待处理
     */
    WAITING_PROCESSED(1),
    /**
     * 等待领取
     */
    WAITING_CLAIM(2),

    /**
     * 等待审批
     */
    WAITING_APPROVAL(3),

    /**
     * 领取处理
     */
    WAITING_CLAIM_PROCESSED(4);

    private int code;

    TaskStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
